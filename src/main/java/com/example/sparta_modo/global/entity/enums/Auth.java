package com.example.sparta_modo.global.entity.enums;

import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public enum Auth {
    USER,
    ADMIN;

    public static Auth of(String authName) throws IllegalArgumentException {
        //TODO 리팩토링 고려
        for (Auth auth : values()) {
            if (auth.name().equals(authName)) {
                return auth;
            }
        }

        throw new CommonException(ErrorCode.ILLIGAL_ARGUMENT, "해당하는 이름의 권한을 찾을 수 없습니다: "+authName );
    }
}
