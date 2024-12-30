package com.example.sparta_modo.domain.user.security.exception;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException)
		throws IOException {

		CommonException commonException = new CommonException(ErrorCode.FORBIDDEN_ACCESS, ErrorCode.FORBIDDEN_ACCESS.getDetail());

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(
			commonException.toErrorResponseDto()
		));
	}
}
