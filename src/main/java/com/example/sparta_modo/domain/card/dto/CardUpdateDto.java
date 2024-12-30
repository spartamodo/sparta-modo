package com.example.sparta_modo.domain.card.dto;

import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CardUpdateDto {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime deadline;

    private Long assigneeId;

    private String changeLog;

    public Card toEntity(User assignee) {
        return Card.builder()
                .name(name)
                .description(description)
                .deadline(deadline)
                .assignee(assignee)
                .build();
    }
}
