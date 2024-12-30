package com.example.sparta_modo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageErrorCode {
    // todo 카드 구현에 따라 수정 체크하기
    NO_EXTENSION_FILE(HttpStatus.BAD_REQUEST, "해당 파일의 확장자가 없습니다."),
    NOT_ALLOW_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "해당 파일은 허용되지 않는 확장자입니다."),
    FAILED_UPLOAD_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 이미지를 저장할 수 없습니다."),
    NO_FILE(HttpStatus.BAD_REQUEST, "파일을 첨부하지 않았습니다."),
    TOO_MANY_FILES(HttpStatus.BAD_REQUEST, "파일이 너무 많습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
