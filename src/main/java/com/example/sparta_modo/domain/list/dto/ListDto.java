package com.example.sparta_modo.domain.list.dto;

import com.example.sparta_modo.global.entity.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ListDto {
    @Getter
    public static class Request {

        @NotBlank(message = "제목을 입력해주세요")
        private final String title;

        @JsonCreator
        public Request(String title) {
            this.title = title;
        }
    }

    @Getter
    public static class Response {

        private final Long listId;
        private final String title;

        public Response(List list) {
            this.listId = list.getId();
            this.title = list.getTitle();
        }
    }
}
