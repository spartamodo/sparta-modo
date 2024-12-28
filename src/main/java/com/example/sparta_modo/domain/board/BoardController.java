package com.example.sparta_modo.domain.board;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> createBoard (@PathVariable Long workspaceId,
                                          @ModelAttribute BoardDto.Request boardDto) {
        boardService.createBoard(boardDto, workspaceId);
        return null;
    }
}
