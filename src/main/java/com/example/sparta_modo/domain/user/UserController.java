package com.example.sparta_modo.domain.user;

import static com.example.sparta_modo.domain.user.security.JwtUtil.AUTHORIZATION_HEADER;

import com.example.sparta_modo.domain.user.dto.LoginDto;
import com.example.sparta_modo.domain.user.dto.MsgDto;
import com.example.sparta_modo.domain.user.dto.SignUpDto;
import com.example.sparta_modo.domain.user.security.JwtUtil;
import com.example.sparta_modo.global.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	private final JwtUtil jwtUtil;

	@PostMapping
	public ResponseEntity<SignUpDto.Response> signUp(
		@Valid @RequestBody SignUpDto.Request requestDto
	) {

		SignUpDto.Response response = userService.signUp(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginDto.Response> login(
		@Valid @RequestBody LoginDto.Request requestDto,
		HttpServletResponse res
	) {

		LoginDto.Response loginDtoResponse = userService.confirmLoginRequest(requestDto);

		String token = jwtUtil.createToken(loginDtoResponse.getEmail(), loginDtoResponse.getAuth());

		jwtUtil.addJwtToCookie(token, res);

		return ResponseEntity.status(HttpStatus.CREATED).body(loginDtoResponse);
	}

	@DeleteMapping
	public ResponseEntity<MsgDto> resign(
		@AuthenticationPrincipal User user,
		HttpServletResponse response
	){
		userService.deleteUser(user.getEmail());

		// JWT 삭제 비우기
		Cookie jwtCookie = new Cookie(AUTHORIZATION_HEADER, "");

		jwtCookie.setMaxAge(0);
		jwtCookie.setPath("/");

		response.addCookie(jwtCookie);

		return ResponseEntity.status(HttpStatus.OK).body(new MsgDto("유저 탈퇴 완료"));
	}

	@PostMapping("/logout")
	public ResponseEntity<MsgDto> logout(
		HttpServletResponse response
	){

		// JWT 삭제 비우기
		Cookie jwtCookie = new Cookie(AUTHORIZATION_HEADER, "");

		jwtCookie.setMaxAge(0);
		jwtCookie.setPath("/");

		response.addCookie(jwtCookie);

		return ResponseEntity.status(HttpStatus.OK).body(new MsgDto("유저 로그아웃 완료"));
	}

}
