package com.example.sparta_modo.global.exception;

import com.example.sparta_modo.global.exception.errorcode.UserErrorCode;
import com.example.sparta_modo.global.exception.response.ErrorResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends ParentException{

    private UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public HttpStatus getHttpStatus(){
        return errorCode.getHttpStatus();
    }

    @Override
    public ErrorResponseDto toErrorResponseDto() {
        //return ErrorResponseDto.builder()
        //todo
        return null;
    }
}

//{
//        "status": 400,
//        "error": "BAD_REQUEST",
//        "code": "BAD_REQUEST_YEAR_MONTH",
//        "massage": "연도 혹은 달을 잘못 입력하셨습니다.",
//        "timestamp": "2024-12-06T02:10:46.0556937"
//        }