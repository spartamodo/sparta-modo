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

    public SequenceListDto.Response createList(User user, Long workspaceId, Long boardId, SequenceListDto.Request request) {
        userAuthorization(user, workspaceId);

        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);

        // 해당 보드의 가장 높은 우선순위값을 찾음
        Optional<Long> maxPriority = findBoard.getList().stream()
                .map(SequenceList::getPriority)
                .max(Long::compareTo);

        // 만약 빈값이면 기본값 FIRST_PRIORITY = 100 을 부여
        if (maxPriority.isEmpty()) {
            SequenceList list = new SequenceList(findBoard, request.getTitle(), FIRST_PRIORITY);
            return new SequenceListDto.Response(sequenceListRepository.save(list));
        } else {
            // 빈값이 아니라면 반올림 해주고 2번째자리에서 반올림후 100곱하고 +300
            Long nextPriority = handlingMaxPriority(maxPriority.get());
            SequenceList list = new SequenceList(findBoard, request.getTitle(), nextPriority);
            return new SequenceListDto.Response(sequenceListRepository.save(list));
        }
    }

    private Long handlingMaxPriority(Long maxPriority) {
        return Math.round(maxPriority / 100.0) * 100 + GREEN_HOPPER_SEQ;
    }

    @Transactional
    public List<SequenceListDto.Response> updateSequence(User user, Long workspaceId, Long boardId, Long listId, @Valid Long moveTo) {

        userAuthorization(user, workspaceId);


        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);
        SequenceList findList = sequenceListRepository.findListByIdOrElseThrow(listId);

        List<SequenceList> originalSequenceList = findBoard.getList();

        if (originalSequenceList.isEmpty() || originalSequenceList.size() == 1) {
            throw new CommonException(ErrorCode.BAD_REQUEST, "리스트가 부족해서 순서 변경을 하지 못합니다.");
        }

        List<Long> sortedList = originalSequenceList.stream().map(SequenceList::getPriority).sorted().toList();

        Long PrioritizePriority = Prioritize(moveTo, findList, sortedList);
        findList.updatePriority(PrioritizePriority);

        return sequenceListRepository.orderdList(findBoard);
    }

    private Long Prioritize(Long moveTo, SequenceList findList, List<Long> sortedList) {
        Long minPriority = sortedList.get(0);
        Long maxPriority = sortedList.get(sortedList.size() - 1);

        if (moveTo == 1L) {
            return (long) (minPriority / 2.0);
        } else if (moveTo == sortedList.size()) {
            return handlingMaxPriority(maxPriority);
        } else {
            Long afterPriority = sortedList.get((int) (moveTo - 2L));
            Long beforePriority = sortedList.get((int) (moveTo - 1L));

            if (findList.getPriority() < beforePriority) {
                afterPriority = beforePriority;
                beforePriority = sortedList.get(moveTo.intValue());
            }

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
