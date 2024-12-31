package com.example.sparta_modo.domain.user.dto;

import com.example.sparta_modo.global.annotation.ValidEnum;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.enums.Auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SignUpDto {

	@Getter
	public static class Request {

		@NotBlank(message = "email 입력은 필수입니다")
		@Email(message = "올바른 email 형식이 아닙니다")
		private String email;

		@NotBlank(message = "password 입력은 필수입니다")
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 최소 8글자 이상이며, 영문, 숫자, 특수문자를 1개씩 포함해야합니다.")
		private String password;

		@NotBlank(message = "nickname은 빈 값이 허용되지 않습니다")
		private String nickname;

		@ValidEnum(enumClass = Auth.class, message = "유효하지 않은 auth입니다.")
		private String auth;

		public User toEntity(PasswordEncoder encoder) {
			return User.builder()
				.email(email)
				.password(encoder.encode(password))
				.nickname(nickname)
				.auth(Auth.of(auth))
				.build();
		}

	}

	@Getter
	public static class Response {
		private Long userId;

		private String auth;

		public Response(Long userId, String auth) {
			this.userId = userId;
			this.auth = auth;
		}
	}

}