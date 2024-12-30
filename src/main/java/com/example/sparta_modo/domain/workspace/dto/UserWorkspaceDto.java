package com.example.sparta_modo.domain.workspace.dto;

import com.example.sparta_modo.global.annotation.ValidEnum;
import com.example.sparta_modo.global.entity.enums.Role;
import lombok.Getter;

public class UserWorkspaceDto {

    @Getter
    public static class Request{

        @ValidEnum(enumClass = Role.class, message = "유효하지 않은 role입니다.")
        private Role role;
    }

    @Getter
    public static class Response{
        private Long workspaceId;

        private Long userId;

        private Role role;

        public Response(Long workspaceId, Long userId, Role role) {
            this.workspaceId = workspaceId;
            this.userId = userId;
            this.role = role;
        }
    }
}
