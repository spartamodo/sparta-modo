package com.example.sparta_modo.domain.list.dto;

import com.example.sparta_modo.global.entity.SequenceList;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class SequenceListDto {
    @Getter
    public static class Request {

        @NotBlank(message = "제목을 입력해주세요")
        private final String title;

        public Request(String title) {
            this.title = title;
        }
    }

    @Getter
    public static class Response {

        private final Long listId;
        private final String title;

        public Response(SequenceList list) {
            this.listId = list.getId();
            this.title = list.getTitle();
        }

        public Response(Long id, String title) {
            this.listId = id;
            this.title = title;
        }
    }
}
