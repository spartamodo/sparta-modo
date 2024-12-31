package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.domain.board.BoardRepository;
import com.example.sparta_modo.domain.list.dto.SequenceListDto;
import com.example.sparta_modo.domain.workspace.UserWorkspaceRepository;
import com.example.sparta_modo.global.entity.Board;
import com.example.sparta_modo.global.entity.SequenceList;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.UserWorkspace;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SequenceListService {

    private final SequenceListRepository sequenceListRepository;
    private final BoardRepository boardRepository;
    private final UserWorkspaceRepository userWorkspaceRepository;

    private final Long FIRST_PRIORITY = 300L;
    private final Long GREEN_HOPPER_SEQ = 300L;

    /**
     * 리스트 저장 시, priority 부여
     * @param user 로그인한 유저
     * @param workspaceId 해당 워크스페이스 식별자 아이디
     * @param boardId 해당 보드 식별자 아이디
     * @param request 제목
     * @return
     */
    public SequenceListDto.Response createList(User user, Long workspaceId, Long boardId, SequenceListDto.Request request) {
        userAuthorization(user, workspaceId);

        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);

        // 해당 보드의 가장 높은 우선순위값을 찾음
        Optional<Long> maxPriority = findBoard.getList().stream()
                .map(SequenceList::getPriority)
                .max(Long::compareTo);

        // 만약 빈값이면 기본값 FIRST_PRIORITY = 300 을 부여
        if (maxPriority.isEmpty()) {
            SequenceList list = new SequenceList(findBoard, request.getTitle(), FIRST_PRIORITY);
            return new SequenceListDto.Response(sequenceListRepository.save(list));
        } else {
            // 빈값이 아니라면 반올림 해주고 2번째자리에서 반올림후 100곱하고 +300 (300, 600, 900 ....)
            Long nextPriority = handlingMaxPriority(maxPriority.get());
            SequenceList list = new SequenceList(findBoard, request.getTitle(), nextPriority);
            return new SequenceListDto.Response(sequenceListRepository.save(list));
        }
    }

    private Long handlingMaxPriority(Long maxPriority) {
        return Math.round(maxPriority / 100.0) * 100 + GREEN_HOPPER_SEQ;
    }

    /**
     * 리스트 드래그-드랍 다운에 의한 순서 변경 서비스
     * @param user 로그인한 유저
     * @param workspaceId 해당 워크스페이스 식별자 아이디
     * @param boardId 해당 보드 식별자 아이디
     * @param listId 순서를 원하는 리스트 아이디
     * @param moveTo 원하는 리스트 위치 첫번째 1, 두번째 2, ....
     * @return 드래그 - 드랍 후, 리스트의 순서대로 반환
     */
    @Transactional
    public List<SequenceListDto.Response> updateSequence(User user, Long workspaceId, Long boardId, Long listId, @Valid Long moveTo) {

        userAuthorization(user, workspaceId);

        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);
        SequenceList findList = sequenceListRepository.findListByIdOrElseThrow(listId);

        List<SequenceList> originalSequenceList = findBoard.getList();

        // 리스트가 비어있거나, 1개일 때, 순서 변경 시, 예외 발생
        if (originalSequenceList.isEmpty() || originalSequenceList.size() == 1) {
            throw new CommonException(ErrorCode.BAD_REQUEST, "리스트가 부족해서 순서 변경을 하지 못합니다.");
        }

        // priority 기준으로 오름차순 정렬 ex) 150, 300, 1500 ...
        List<Long> sortedList = originalSequenceList.stream().map(SequenceList::getPriority).sorted().toList();

        //todo 순서를 저장하기 위해, 해당 우선순위에 몇개가 들어있는지 계산해야함
        // green-hopper 방식으로 기본 리스트 값은 300개가 들어갈 수 있지만, 100개 이상부터 충돌 가능성
        // ex) 0~300 안에 50개가 넘는다면 해당 보드 안에 리스트의 priority 를 재부여하는 로직을 짜야함.

        // 우선 순위를 계산
        Long PrioritizePriority = Prioritize(moveTo, findList, sortedList);
        findList.updatePriority(PrioritizePriority);

        return sequenceListRepository.orderdList(findBoard);
    }

    private Long Prioritize(Long moveTo, SequenceList findList, List<Long> sortedList) {
        // 가장 낮은 우선순위 값
        Long minPriority = sortedList.get(0);

        // 가장 높은 우선순위 값
        Long maxPriority = sortedList.get(sortedList.size() - 1);

        // 첫번째 위치로 변경 시,
        if (moveTo == 1L) {
            if (findList.getPriority().equals(minPriority)) {
                throw new CommonException(ErrorCode.BAD_REQUEST, "같은 자리로 순서를 변경하지 못합니다.");
            }

            return (long) (minPriority / 2.0);

        // 마지막 위치로 원할 시,
        } else if (moveTo == sortedList.size()) {
            if (findList.getPriority().equals(maxPriority)) {
                throw new CommonException(ErrorCode.BAD_REQUEST, "같은 자리로 순서를 변경하지 못합니다.");
            }

            return handlingMaxPriority(maxPriority);

        // 그 외, 사이에 위치시킬 때,
        } else {
            if (findList.getPriority().equals(sortedList.get((int) (moveTo - 1L)))) {
                throw new CommonException(ErrorCode.BAD_REQUEST, "같은 자리로 순서를 변경하지 못합니다.");
            }

            // 원하는 위치의 앞쪽 리스트 우선순위 값
            Long afterPriority = sortedList.get((int) (moveTo - 2L));
            // 원하는 위치의 뒤쪽 리스트 우선순위 값
            Long beforePriority = sortedList.get((int) (moveTo - 1L));

            // 만약 우선순위값이 원하는 위치의 뒤쪽리스트 값보다 낮다면 우선순위를 재부여
            // why? 젤 첫번째 리스트를 옮긴다면 해당 첫번째 리스트는 없다고 가정하고 순위를 짜야하기 때문
            if (findList.getPriority() < beforePriority) {
                afterPriority = beforePriority;
                beforePriority = sortedList.get(moveTo.intValue());
            }

            // 앞 쪽, 뒷 쪽 리스트의 우선순위의 중간값을 반환
            return (long) ((afterPriority + beforePriority) / 2.0);
        }
    }

    private void userAuthorization(User user, Long workspaceId) {
        UserWorkspace userWorkspace
                = userWorkspaceRepository.findByWorkspaceIdAndUser(user, workspaceId);

        if (userWorkspace == null) {
            throw new CommonException(ErrorCode.FORBIDDEN_ACCESS, "해당 워크스페이스에 대한 접근 권한이 없습니다.");
        }
    }

}
