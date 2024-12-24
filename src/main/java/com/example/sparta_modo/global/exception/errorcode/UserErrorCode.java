package com.example.sparta_modo.global.exception.errorcode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {
    NOT_FOUND_VALUE(HttpStatus.NOT_FOUND, "해당 정보가 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
