package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {CommonException.class})
    public ResponseEntity<ErrorResponseDto> handleCommonException(CommonException exception) {
        return ErrorResponseDto.toResponseEntity(exception);
    }

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomException(UserException exception) {
        return ErrorResponseDto.toResponseEntity(exception);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponseDto responseDto = ErrorResponseDto.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .code("VALIDATION_ERROR")
            .massage(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
