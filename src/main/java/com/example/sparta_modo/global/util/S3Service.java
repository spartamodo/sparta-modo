package com.example.sparta_modo.global.util;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import com.example.sparta_modo.domain.card.dto.CardDto;

import java.io.IOException;

public interface S3Service {

    void uploadImage(BoardDto.Request boardDto, Long boardId) throws IOException;

    void uploadFiles(CardDto.Request cardDto, Long cardId) throws IOException;
}
