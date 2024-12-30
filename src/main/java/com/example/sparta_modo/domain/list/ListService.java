package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.domain.board.BoardRepository;
import com.example.sparta_modo.domain.list.dto.ListDto;
import com.example.sparta_modo.global.entity.Board;
import com.example.sparta_modo.global.entity.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;
    private final Long FIRST_PRIORITY = 100L;
    private final Long GREEN_HOPPER_SEQ = 300L;

    public ListDto.Response createList(Long boardId, ListDto.Request request) {
        Board findBoard = boardRepository.findBoardByIdOrElseThrow(boardId);

        // 해당 보드의 가장 높은 우선순위값을 찾음
        Optional<Long> maxPriority = findBoard.getList().stream()
                .map(List::getPriority)
                .max(Long::compareTo);

        // 만약 빈값이면 기본값 FIRST_PRIORITY = 100 을 부여
        if (maxPriority.isEmpty()) {
            List list = new List(findBoard, request.getTitle(), FIRST_PRIORITY);
            return new ListDto.Response(listRepository.save(list));
        } else {
            // 빈값이 아니라면 반올림 해주고 2번째자리에서 반올림후 100곱하고 +300
            Long nextPriority = handlingMaxPriority(maxPriority.get());
            List list = new List(findBoard, request.getTitle(), nextPriority);
            return new ListDto.Response(listRepository.save(list));
        }
    }

    private Long handlingMaxPriority(Long maxPriority) {
        return Math.round(maxPriority / 100.0) * 100 + GREEN_HOPPER_SEQ;
    }
}
