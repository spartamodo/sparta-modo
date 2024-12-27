package com.example.sparta_modo.global.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "workspace")
@NoArgsConstructor
public class Workspace extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Builder
    public Workspace(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
