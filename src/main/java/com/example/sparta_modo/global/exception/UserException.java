package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.errorcode.UserErrorCode;
import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends ParentException{

    private UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        this.errorCode = errorCode;
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
            .massage(errorCode.getDetail())
            .build();
    }
}