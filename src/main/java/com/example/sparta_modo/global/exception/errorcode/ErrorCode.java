package com.example.sparta_modo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD REQUEST
    //BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 401 UNAUTHORIZED(User, Workspace, Board, List, Card, Comment 모든 Unauthorized)
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"인증에 실패했습니다."),

    // 403 FORBIDDEN (User, Workspace, Board, List, Card, Comment 모든 Forbidden)
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "권한이 없는 접근입니다."),

    // 404 NOT FOUND (User, Workspace, Board, List, Card, Comment 모든 Not Found)
    NOT_FOUND_VALUE(HttpStatus.NOT_FOUND, "해당 정보가 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
