package com.example.sparta_modo.domain.card.file;

import com.example.sparta_modo.domain.card.file.dto.FileCreateDto;
import com.example.sparta_modo.domain.card.file.dto.FileFindDto;
import com.example.sparta_modo.global.dto.MsgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/cards/{cardId}/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    // 첨부파일 추가
    @PostMapping
    public ResponseEntity<Map<String, Object>> uploadFile(
            @PathVariable Long cardId,
            @ModelAttribute FileCreateDto file) throws IOException {

        Map<String, Object> response = fileService.uploadFiles(file, cardId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 첨부파일 조회
    @GetMapping
    public ResponseEntity<FileFindDto> getFiles(@PathVariable Long cardId) {

        FileFindDto files = fileService.findFiles(cardId);

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    // 첨부파일 삭제
    @DeleteMapping("/{fileId}")
    public ResponseEntity<MsgDto> deleteFile(@PathVariable Long fileId) {

        fileService.deleteFile(fileId);

        return ResponseEntity.status(HttpStatus.OK).body(new MsgDto("파일이 성공적으로 삭제되었습니다."));
    }
}
