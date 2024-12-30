package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.errorcode.ImageErrorCode;
import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ImageException extends ParentException{

    private final ImageErrorCode errorCode;

    public ImageException(ImageErrorCode errorCode) {
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