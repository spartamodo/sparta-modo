package com.example.sparta_modo.domain.board.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class BoardDto {

    @Getter
    public static class Request {

        private MultipartFile image;

        private String title;

        private String description;

        private String backgroundColor;

        private int imageActivated;

    }
}
