package com.example.sparta_modo.domain.card;

import com.example.sparta_modo.domain.card.dto.CardCreateDto;
import com.example.sparta_modo.domain.card.dto.CardFindDto;
import com.example.sparta_modo.domain.card.dto.CardUpdateDto;
import com.example.sparta_modo.domain.dto.MsgDto;
import com.example.sparta_modo.domain.user.UserRepository;
import com.example.sparta_modo.domain.workspace.UserWorkspaceRepository;
import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.CardHistory;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.UserWorkspace;
import com.example.sparta_modo.global.entity.enums.Role;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final SequenceListRepository sequenceListRepository;
    private final UserRepository userRepository;
    private final UserWorkspaceRepository userWorkspaceRepository;
    private final CardHistoryRepository cardHistoryRepository;

    // 유저 워크스페이스 조회 및 기능 검증
    private void checkReadOnly(User loginUser, Long workspaceId) {
        UserWorkspace userWorkspace = userWorkspaceRepository.findByWorkspaceIdAndUser(loginUser, workspaceId);

        if (userWorkspace == null) {
            throw new CommonException(ErrorCode.FORBIDDEN_ACCESS, "해당 워크스페이스에 대한 접근 권한이 없습니다.");
        }

        // READ_ONLY 권한 검증
        if (userWorkspace.getRole() == Role.READ_ONLY) {
            throw new CommonException(ErrorCode.FORBIDDEN_ACCESS, "READ_ONLY 권한을 가진 유저는 카드 생성이 불가능합니다.");
        }
    }

    // 담당자 확인
    private User checkAssignee(Long requestDto) {
        // 담당자 조회 및 검증
        User assignee = userRepository.findById(requestDto)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 담당자 ID 입니다."));
        return assignee;
    }

    // 카드 생성 메서드
    public CardCreateDto createCard(User loginUser, CardCreateDto requestDto) {

        // 리스트 조회 및 검증
        List list = sequenceListRepository.findById(requestDto.getSequenceListId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 리스트ID 입니다."));

        // 워크스페이스 접근 권한 검증
        checkReadOnly(loginUser, sequenceList.getBoard().getWorkspace().getId());

        // 담당자 조회 및 검증
        User assignee = checkAssignee(requestDto.getAssigneeId());

        // 엔티티 생성
        Card card = requestDto.toEntity(sequenceList, assignee);

        // 엔티티 저장
        Card savedCard = cardRepository.save(card);

        // 결과 반환 (빌더 패턴 적용)
        return CardCreateDto.builder()
                .listId(savedCard.getSequenceList().getId())
                .name(savedCard.getName())
                .description(savedCard.getDescription())
                .deadline(savedCard.getDeadline())
                .assigneeId(savedCard.getAssignee().getId())
                .build();
    }

    // 카드 수정 메서드
    public CardUpdateDto updateCard(User loginUser, Long cardId, CardUpdateDto requestDto) {

        // 카드 조회 및 검증
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        // 워크스페이스 접근 권한 검증
        checkReadOnly(loginUser, card.getList().getBoard().getWorkspace().getId());

        // 담당자 조회 및 검증
        User assignee = checkAssignee(requestDto.getAssigneeId());

        // 카드 정보 수정
        card.updateCard(requestDto.getName(), requestDto.getDescription(), requestDto.getDeadline(), assignee);

        // 카드 변경 사항 저장
        cardHistoryRepository.save(new CardHistory(card, requestDto.getChangeLog()));

        // 저장 및 반환 최적화
        return CardUpdateDto.builder()
                .id(card.getId())
                .name(card.getName())
                .description(card.getDescription())
                .deadline(card.getDeadline())
                .assigneeId(card.getAssignee().getId())
                .build();
    }

    // 카드 조회 메서드
    public CardFindDto findCard(Long cardId) {

        // 카드 조회 및 검증
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        List<CardHistory> historyList = cardHistoryRepository.findByCardId(cardId);

        return new CardFindDto(card, historyList);
    }

    // 카드 삭제 메서드
    public MsgDto deleteCard(Long cardId, User loginUser) {

        // 카드 조회 및 검증
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "존재하지 않는 카드 ID 입니다."));

        checkReadOnly(loginUser, card.getList().getBoard().getWorkspace().getId());

        cardRepository.delete(card);

        return new MsgDto("카드 삭제가 완료되었습니다.");
    }
}
