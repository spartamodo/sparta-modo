package com.example.sparta_modo.global.exception.errorcode;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    NOT_MATCH_PASSOWORD(HttpStatus.BAD_REQUEST, "비밀번호가 맞지 안습니다"),

    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, "이미 삭제된 유접입니다");

    private final HttpStatus httpStatus;
    private final String detail;
}
