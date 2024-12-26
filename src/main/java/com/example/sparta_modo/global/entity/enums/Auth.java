package com.example.sparta_modo.global.entity.enums;

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

        throw new IllegalArgumentException("해당하는 이름의 권한을 찾을 수 없습니다: " + authName);
    }
}
