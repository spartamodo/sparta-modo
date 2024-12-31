package com.example.sparta_modo.domain.board;

import com.example.sparta_modo.global.entity.Board;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    default Board findBoardByIdOrElseThrow(Long boardId){
        return findBoardById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "보드를 찾을 수 없습니다"));
    }

    Optional<Board> findBoardById(Long boardId);
}
