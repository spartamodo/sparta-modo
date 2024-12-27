package com.example.sparta_modo.domain.user.dto;

import com.example.sparta_modo.global.entity.enums.Auth;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LoginDto {

	@Getter
	public static class Request {

		@NotBlank(message = "이메일을 입력해주세요")
		private String email;

		@NotBlank(message = "비밀번호를 입력해주세요")
		private String password;

	}


	@Getter
	public static class Response {

		private Long userId;

		private String email;

		private Auth auth;

		public Response(Long userId, String email, Auth auth) {
			this.userId = userId;
			this.email = email;
			this.auth = auth;
		}
	}

}
