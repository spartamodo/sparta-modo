package com.example.sparta_modo.global.entity;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "board")
@NoArgsConstructor
@DynamicUpdate
public class Board extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private String backgroundColor;

    @OneToMany(mappedBy = "board")
    private List<SequenceList> list = new ArrayList<>();

    @OneToOne(mappedBy = "board")
    private BoardImage boardImage;

    @Column(nullable = false, columnDefinition = "bit")
    private int imageActivated;

    public Board (Workspace workspace, BoardDto.Request request) {
        this.workspace = workspace;
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.backgroundColor = request.getBackgroundColor();
        this.imageActivated = request.getImageActivated();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void updateImageActivated(Integer imageActivated) {
        this.imageActivated = imageActivated;
    }
}
