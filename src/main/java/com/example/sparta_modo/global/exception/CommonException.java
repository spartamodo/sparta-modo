package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;

public class CommonException extends ParentException{

	private final ErrorCode errorCode;
	private final String msg;

	public CommonException(ErrorCode errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}

	@Override
	public HttpStatus getHttpStatus(){
		return errorCode.getHttpStatus();
	}

	@Override
	public ErrorResponseDto toErrorResponseDto() {
		return ErrorResponseDto.builder()
			.status(errorCode.getHttpStatus().value())
			.error(errorCode.getHttpStatus().getReasonPhrase())
			.code(errorCode.toString())
			.massage(msg)
			.build();
	}
}
