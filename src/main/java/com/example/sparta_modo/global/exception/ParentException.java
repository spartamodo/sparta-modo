package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;

public abstract class ParentException extends RuntimeException{

    public abstract HttpStatus getHttpStatus();

    public abstract ErrorResponseDto toErrorResponseDto();
}
