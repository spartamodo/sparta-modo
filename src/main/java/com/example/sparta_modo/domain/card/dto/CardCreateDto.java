package com.example.sparta_modo.domain.card.dto;

import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.SequenceList;
import com.example.sparta_modo.global.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder // 빌더 패턴 적용
public class CardCreateDto {

    @NotBlank(message = "리스트 ID는 필수 입력값입니다.")
    @Positive(message = "리스트 ID는 양수여야 합니다.")
    private Long sequenceListId;

    @NotBlank(message = "카드 이름은 필수 입력값입니다.")
    private String name;

    private String description;

    private LocalDateTime deadline;

    @NotBlank(message = "담당자 ID는 필수 입력값입니다.")
    @Positive(message = "담당자 ID는 양수여야 합니다.")
    private Long assigneeId;

    public Card toEntity(SequenceList sequenceListId, User assignee) {
        return Card.builder()
                .list(sequenceListId)
                .name(name)
                .description(description)
                .deadline(deadline)
                .assignee(assignee)
                .build();
    }
}

