package com.example.sparta_modo.domain.card.file;

import com.example.sparta_modo.domain.card.CardRepository;
import com.example.sparta_modo.domain.card.file.dto.FileCreateDto;
import com.example.sparta_modo.domain.card.file.dto.FileFindDto;
import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.File;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import com.example.sparta_modo.global.util.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final CardRepository cardRepository;

    // 파일 업로드
    public Map<String, Object> uploadFiles(FileCreateDto file, Long cardId) throws IOException {

        // Null 체크 추가
        if (file.getFile() == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "파일을 찾을 수 없습니다.");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        List<String> fileUrls = s3Service.uploadFiles(file, cardId);

        for (String fileUrl : fileUrls) {
            File uploadedFile = new File(card, fileUrl);

            fileRepository.save(uploadedFile);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cardId", cardId);
        response.put("fileUrls", fileUrls);

        return response;
    }

    // 첨부파일 조회
    public FileFindDto findFiles(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        List<File> fileList = fileRepository.findByCard(card);

        return new FileFindDto(cardId, fileList);
    }

    // 파일 삭제
    public void deleteFile(Long fileId) {

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "파일을 찾을 수 없습니다."));

        fileRepository.delete(file);
    }
}