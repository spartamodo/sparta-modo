package com.example.sparta_modo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageErrorCode {
    NO_EXTENSION_FILE(HttpStatus.BAD_REQUEST, "해당 파일의 확장자가 없습니다."),
    NOT_ALLOW_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "해당 파일은 허용되지 않는 확장자입니다."),
    FAILED_UPLOAD_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 이미지를 저장할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
