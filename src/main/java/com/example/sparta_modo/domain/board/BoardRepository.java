package com.example.sparta_modo.domain.board;

import com.example.sparta_modo.global.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
