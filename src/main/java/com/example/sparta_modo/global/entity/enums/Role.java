package com.example.sparta_modo.global.entity.enums;

import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;

public enum Role {
    ADMIN, MANAGER, WORKER, READ_ONLY;

    public static Role of(String roleName) throws IllegalArgumentException {

        for (Role role : values()) {
            if (role.name().equals(roleName)) {
                return role;
            }
        }

        throw new CommonException(ErrorCode.ILLIGAL_ARGUMENT, "해당하는 이름의 역할을 찾을 수 없습니다: "+roleName );
    }
}
