package com.example.sparta_modo.global.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "workspace")
@NoArgsConstructor
@DynamicUpdate
public class Workspace extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @OneToMany(mappedBy = "workspace")
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Workspace(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void updateWorkspace(String title, String description) {
        if(title != null && !title.isEmpty()) {
            this.title = title;
        }
        if(description != null && !description.isEmpty()) {
            this.description = description;
        }
    }
}
