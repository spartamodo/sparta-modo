package com.example.sparta_modo.domain.card;

import com.example.sparta_modo.domain.card.dto.CardCreateDto;
import com.example.sparta_modo.domain.user.UserRepository;
import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.List;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final ListRepository listRepository;
    private final UserRepository userRepository;

    // 카드 생성 메서드
    public CardCreateDto createCard(User loginUser, CardCreateDto requestDto) {

        // 리스트 조회 및 검증
        List list = listRepository.findById(requestDto.getListId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 리스트ID 입니다."));

        // 담당자 조회 및 검증
        User assignee = userRepository.findById(requestDto.getAssigneeId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 담당자 ID 입니다."));

        // 엔티티 생성
        Card card = requestDto.toEntity(list, assignee);

        // 엔티티 저장
        Card savedCard = cardRepository.save(card);

        // 결과 반환 (빌더 패턴 적용)
        return CardCreateDto.builder()
                .listId(savedCard.getList().getId())
                .name(savedCard.getName())
                .description(savedCard.getDescription())
                .deadline(savedCard.getDeadline())
                .assigneeId(savedCard.getAssignee().getId())
                .build();
    }
}
