package com.example.sparta_modo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum WorkspaceErrorCode {

    ALREADY_DECIDED_ACCESS(HttpStatus.BAD_REQUEST, "이미 처리된 요청입니다");

    private final HttpStatus httpStatus;
    private final String detail;
}
