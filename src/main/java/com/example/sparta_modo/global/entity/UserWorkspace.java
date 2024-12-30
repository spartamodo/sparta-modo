package com.example.sparta_modo.global.entity;

import com.example.sparta_modo.global.entity.enums.Auth;
import com.example.sparta_modo.global.entity.enums.InvitingStatus;
import com.example.sparta_modo.global.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user_workspace")
@NoArgsConstructor
public class UserWorkspace extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name ="workspace_id")
    private Workspace workspace;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private InvitingStatus invitingStatus;

    @Builder
    public UserWorkspace(User user ,Workspace workspace, Role role, InvitingStatus invitingStatus) {
        this.user = user;
        this.workspace = workspace;
        this.role = role;
        this.invitingStatus = invitingStatus;

    }
    // 사용자 역할 수정
    public void updateRole(Role role) {
        this.role = role;
    }
}
