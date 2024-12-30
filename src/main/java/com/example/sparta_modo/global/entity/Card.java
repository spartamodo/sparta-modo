package com.example.sparta_modo.global.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity(name = "card")
@NoArgsConstructor
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sequence_list_id")
    private SequenceList sequenceList;

    @Column(nullable = false)
    private String name;

    private String description;

    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignee;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardHistory> changeLogs;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files;

    public Card(SequenceList list, String name, String description, LocalDate deadline, User assignee) {
        this.sequenceList = list;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.assignee = assignee;
    }

    public void updateCard(String name, String description,LocalDate deadline,User assignee) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.assignee = assignee;
    }
}
