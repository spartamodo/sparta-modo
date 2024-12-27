package com.example.sparta_modo.domain.workspace.dto;

import com.example.sparta_modo.global.entity.Workspace;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class WorkspaceDto {

    @Getter
    public static class Request {

        @NotBlank(message = "title 입력은 필수입니다")
        private String title;

        private String description;

        public Workspace toEntity() {
            return Workspace.builder()
                    .title(title)
                    .description(description)
                    .build();
        }
    }

    @Getter
    public static class Response {
        private Long workspaceId;

        private String title;

        private String description;

        public Response(Long workspaceId, String title, String description) {
            this.workspaceId = workspaceId;
            this.title = title;
            this.description = description;
        }

        public static Response toDto(Workspace workspace) {
            return new Response(
                    workspace.getId(),
                    workspace.getTitle(),
                    workspace.getDescription()
            );
        }
    }

}
