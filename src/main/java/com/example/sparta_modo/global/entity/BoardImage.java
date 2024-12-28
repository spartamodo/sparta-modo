package com.example.sparta_modo.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "board_image")
@NoArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String url;

}
