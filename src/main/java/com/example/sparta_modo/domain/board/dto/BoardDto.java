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
    public static abstract class ResponseBaseDto {
        private final Long boardId;

        private final String title;

        public ResponseBaseDto(Long boardId, String title) {
            this.boardId = boardId;
            this.title = title;
        }

    }

    @Getter
    public static class listResponse extends ResponseBaseDto {

        public listResponse(Long boardId, String title) {
            super(boardId, title);
        }
    }

    @Getter
    public static class backgroundColorResponse extends ResponseBaseDto {

        private final String backgroundColor;

        public backgroundColorResponse(Long boardId, String title, String backgroundColor) {
            super(boardId, title);
            this.backgroundColor = backgroundColor;
        }
    }

    @Getter
    public static class existImageResponse extends ResponseBaseDto {

        private final String imageUrl;

        public existImageResponse(Long boardId, String title, String imageUrl) {
            super(boardId, title);
            this.imageUrl = imageUrl;
        }
    }

    @Getter
    public static abstract class DetailResponseBaseDto extends ResponseBaseDto {

        private final String description;
        private final int imageActivated;

        public DetailResponseBaseDto(Long boardId, String title, String description, int imageActivated) {
            super(boardId, title);
            this.description = description;
            this.imageActivated = imageActivated;
        }
    }

    @Getter
    public static class backgroundColorDetailResponse extends DetailResponseBaseDto {

        private final String backgroundColor;

        public backgroundColorDetailResponse(Long boardId, String title, String description, int imageActivated, String backgroundColor) {
            super(boardId, title, description, imageActivated);
            this.backgroundColor = backgroundColor;
        }
    }

    @Getter
    public static class imageDetailResponse extends DetailResponseBaseDto {

        private final String imageUrl;

        public imageDetailResponse(Long boardId, String title, String description, int imageActivated, String imageUrl) {
            super(boardId, title, description, imageActivated);
            this.imageUrl = imageUrl;
        }
    }
}
