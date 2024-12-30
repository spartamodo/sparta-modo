package com.example.sparta_modo.domain.card.dto;

import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.CardHistory;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardFindDto {
    private final Long id;
    private final String name;
    private final String description;
    private final List<String> changeLog;

    public CardFindDto(Card card) {
        this.id = card.getId();
        this.name = card.getName();
        this.description = card.getDescription();
        this.changeLog = card.getChangeLog().stream()
                .map(CardHistory::getChangeLog)
                .collect(Collectors.toList());
    }
}
