package com.example.sparta_modo.global.entity;

import com.example.sparta_modo.global.entity.enums.InvitingStatus;
import com.example.sparta_modo.global.entity.enums.Role;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="workspace_id")
    private Workspace workspace;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private InvitingStatus invitingStatus;

}
