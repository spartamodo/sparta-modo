package com.example.sparta_modo.domain.card.dto;

import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.CardHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CardFindDto {
    private final Long id;
    private final String name;
    private final String description;
    private final List<CardHistory> history;

    public CardFindDto(Card card, List<CardHistory> history) {
        this.id = card.getId();
        this.name = card.getName();
        this.description = card.getDescription();
        this.history = history;
    }
}
