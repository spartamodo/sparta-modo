package com.example.sparta_modo.domain.workspace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class WorkspaceInviteDto {

    @Getter
    public static class Request {

        @NotBlank(message = "email 입력은 필수입니다")
        @Email(message = "올바른 email 형식이 아닙니다")
        private String email;

    }

    @Getter
    public static class Response {
        private Long workspaceId;

        private Long userId;

        private String email;

        public Response(Long workspaceId, Long userId, String email) {
            this.workspaceId = workspaceId;
            this.userId = userId;
            this.email = email;
        }
    }
}
