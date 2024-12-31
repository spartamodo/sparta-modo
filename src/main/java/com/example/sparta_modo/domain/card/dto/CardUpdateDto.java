package com.example.sparta_modo.domain.card.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.LocalDate;

public class CardUpdateDto {

    @Getter
    public static class Response {

        private Long id;

        private String name;

        private String description;

        private LocalDate deadline;

        private Long assigneeId;

        @NotEmpty
        private String changeLog;

        public Response(Long id, String name, String description, LocalDate deadline, Long assigneeId, String changeLog) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.deadline = deadline;
            this.assigneeId = assigneeId;
            this.changeLog = changeLog;
        }
    }

    @Getter
    public static class Request{

        private String name;

        private String description;

        private LocalDate deadline;

        private Long assigneeId;

        @NotEmpty
        private String changeLog;

        public Request(String name, String description, LocalDate deadline, Long assigneeId, String changeLog){
            this.name = name;
            this.description = description;
            this.deadline = deadline;
            this.assigneeId = assigneeId;
            this.changeLog = changeLog;
        }
    }
}
