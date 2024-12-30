package com.example.sparta_modo.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "sequence_list")
@NoArgsConstructor
public class SequenceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long priority;

    public SequenceList (Board board, String title, Long priority) {
        this.board = board;
        this.title = title;
        this.priority = priority;
    }

    public void updatePriority(Long priority) {
        this.priority = priority;
    }
}
