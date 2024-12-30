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
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final CardRepository cardRepository;

    // 파일 업로드
    public void uploadFiles(FileCreateDto files, Long cardId, String username) throws IOException {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        List<String> fileUrls = s3Service.uploadFiles(files, cardId);

        for (String fileUrl : fileUrls) {
            File uploadedFile = new File(card, fileUrl);

            fileRepository.save(uploadedFile);
        }
    }

    // 첨부파일 조회
    public FileFindDto findFiles(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        List<File> file = fileRepository.findByCard(card);

        return new FileFindDto(cardId, file);
    }

    // 파일 삭제
    public void deleteFile(Long fileId) {

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "파일을 찾을 수 없습니다."));

        fileRepository.delete(file);
    }
}