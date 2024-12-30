package com.example.sparta_modo.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CardCreateDto {

    @NotBlank(message = "리스트 ID는 필수 입력값입니다.")
    @Positive(message = "리스트 ID는 양수여야 합니다.")
    private final Long sequenceListId;

    @NotBlank(message = "카드 이름은 필수 입력값입니다.")
    private final String name;

    private final String description;

    private final LocalDate deadline;

    @NotBlank(message = "담당자 ID는 필수 입력값입니다.")
    @Positive(message = "담당자 ID는 양수여야 합니다.")
    private final Long assigneeId;

    public CardCreateDto(Long sequenceListId, String name, String description, LocalDate deadline, Long assigneeId) {
        this.sequenceListId = sequenceListId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.assigneeId = assigneeId;
    }
}

