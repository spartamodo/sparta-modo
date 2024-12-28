package com.example.sparta_modo.global.util;

import com.example.sparta_modo.domain.board.dto.BoardDto;

import java.io.IOException;

public interface S3Service {

    String uploadImage(BoardDto.Request boardDto, Long boardId) throws IOException;

    // todo 카드쪽 구현 시, 수정
//    void uploadFiles(CardDto.Request cardDto, Long cardId) throws IOException;
}
