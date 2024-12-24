package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomException(UserException exception) {
        return ErrorResponseDto.toResponseEntity(exception);
    }
}
