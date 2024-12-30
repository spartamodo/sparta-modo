package com.example.sparta_modo.global.util;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import com.example.sparta_modo.domain.card.file.dto.FileCreateDto;

import java.io.IOException;
import java.util.List;

public interface S3Service {

    String uploadImage(BoardDto.Request boardDto, Long boardId) throws IOException;

    // todo 카드쪽 구현 시, 수정
    List<String> uploadFiles(FileCreateDto dto, Long cardId) throws IOException;
}
