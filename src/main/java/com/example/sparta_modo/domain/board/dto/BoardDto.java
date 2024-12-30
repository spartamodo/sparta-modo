package com.example.sparta_modo.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class BoardDto {

    @AllArgsConstructor
    @Getter
    public static class Request {

        private MultipartFile[] image;

        @NotBlank(message = "title 은 필수입니다.")
        private String title;

        private String description;

        @NotBlank(message = "배경색 은 필수입니다.")
        private String backgroundColor;

        @NotNull(message = "이미지 적용 은 필수입니다.")
        private int imageActivated;

    }

    @Getter
    public abstract static class ResponseBaseDto {
        private final Long boardId;

        private final String title;

        public ResponseBaseDto(Long boardId, String title) {
            this.boardId = boardId;
            this.title = title;
        }

    }

    @Getter
    public static class generalResponse extends ResponseBaseDto {

        private final String backgroundColor;

        public generalResponse(Long boardId, String title, String backgroundColor) {
            super(boardId, title);
            this.backgroundColor = backgroundColor;
        }
    }

    @Getter
    public static class ExistImageResponse extends ResponseBaseDto {

        private final String imageUrl;

        public ExistImageResponse(Long boardId, String title, String imageUrl) {
            super(boardId, title);
            this.imageUrl = imageUrl;
        }
    }
}
