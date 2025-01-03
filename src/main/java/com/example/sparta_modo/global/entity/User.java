package com.example.sparta_modo.global.entity;

import com.example.sparta_modo.global.entity.enums.Auth;
import com.example.sparta_modo.global.entity.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(value = EnumType.STRING)
    private Auth auth;

    @Builder
    public User(String email, String password, String nickname, Auth auth) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.auth = auth;
        userStatus = UserStatus.ACTIVATED;
    }

    public void deleteUser(){
        userStatus = UserStatus.DEACTIVATED;
    }

}
