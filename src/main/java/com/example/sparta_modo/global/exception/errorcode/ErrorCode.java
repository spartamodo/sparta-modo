package com.example.sparta_modo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 401 UNAUTHORIZED(User, Workspace, Board, List, Card, Comment 모든 Unauthorized)
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"인증에 실패했습니다."),

    // 403 FORBIDDEN (User, Workspace, Board, List, Card, Comment 모든 Forbidden)
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없는 접근입니다."),

    // 404 NOT FOUND (User, Workspace, Board, List, Card, Comment 모든 Not Found)
    NOT_FOUND_VALUE(HttpStatus.NOT_FOUND, "해당 정보가 없습니다."),

    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 값입니다."),

    ILLIGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "유효하지 않은 인자 값입니다"),

    JACKSON_IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "처리중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
