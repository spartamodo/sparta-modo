package com.example.sparta_modo.global.entity;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity(name = "board")
@NoArgsConstructor
public class Board {
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

    @ColumnDefault("'#FFFFFF'")
    private String themeColor;

    @Column(nullable = false, columnDefinition = "tinyint")
    private int imageActivated;

    public Board (Workspace workspace, BoardDto.Request request) {
        this.workspace = workspace;
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.themeColor = request.getBackgroundColor();
        this.imageActivated = request.getImageActivated();
    }
}
