package com.example.sparta_modo.domain.board.dto;

import com.example.sparta_modo.global.entity.Board;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

        @Min(0)
        @Max(1)
        private int imageActivated;

        public Request(UpdateRequest updateRequest) {
            this.image = updateRequest.getImage();
            this.title = updateRequest.getTitle();
            this.description = updateRequest.getDescription();
            this.backgroundColor = updateRequest.getBackgroundColor();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class UpdateRequest {

        private MultipartFile[] image;

        private String title;

        private String description;

        private String backgroundColor;

        @Min(0)
        @Max(1)
        private Integer imageActivated;

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
    public static class ListResponse extends ResponseBaseDto {

        public ListResponse(Long boardId, String title) {
            super(boardId, title);
        }
    }

    @Getter
    public static class BackgroundColorResponse extends ResponseBaseDto {

        private final String backgroundColor;

        public BackgroundColorResponse(Long boardId, String title, String backgroundColor) {
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
    public static class BackgroundColorDetailResponse extends DetailResponseBaseDto {

        private final String backgroundColor;

        public BackgroundColorDetailResponse(Long boardId, String title, String description, int imageActivated, String backgroundColor) {
            super(boardId, title, description, imageActivated);
            this.backgroundColor = backgroundColor;
        }
    }

    @Getter
    public static class ImageDetailResponse extends DetailResponseBaseDto {

        private final String imageUrl;

        public ImageDetailResponse(Long boardId, String title, String description, int imageActivated, String imageUrl) {
            super(boardId, title, description, imageActivated);
            this.imageUrl = imageUrl;
        }
    }

    @Getter
    public static class AllDetailResponse extends DetailResponseBaseDto {

        private final String imageUrl;
        private final String backgroundColor;

        public AllDetailResponse(Board findBoard) {
            super(findBoard.getId(), findBoard.getTitle(), findBoard.getDescription(), findBoard.getImageActivated());
            this.imageUrl = findBoard.getBoardImage().getUrl();
            this.backgroundColor = findBoard.getBackgroundColor();
        }
    }
}
