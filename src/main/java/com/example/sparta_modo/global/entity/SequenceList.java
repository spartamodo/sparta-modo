package com.example.sparta_modo.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "sequence_list")
@NoArgsConstructor
public class SequenceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long priority;

    @OneToMany(mappedBy = "sequenceList",cascade = CascadeType.ALL)
    private List<Card> list = new ArrayList<>();

    public SequenceList (Board board, String title, Long priority) {
        this.board = board;
        this.title = title;
        this.priority = priority;
    }

    public void updatePriority(Long priority) {
        this.priority = priority;
    }
}
