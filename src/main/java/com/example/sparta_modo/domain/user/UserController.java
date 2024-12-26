package com.example.sparta_modo.domain.user;

import com.example.sparta_modo.domain.user.dto.SignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<SignUpDto.Response> signUp(
		@Valid @RequestBody SignUpDto.Request requestDto
	) {

		SignUpDto.Response response = userService.signUp(requestDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
