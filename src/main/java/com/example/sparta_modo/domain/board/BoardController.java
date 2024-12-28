package com.example.sparta_modo.domain.board;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDto.ResponseBaseDto> createBoard (@PathVariable Long workspaceId,
                                                                 @Valid @ModelAttribute BoardDto.Request boardDto) {
        BoardDto.ResponseBaseDto boardResponse = boardService.createBoard(boardDto, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardResponse);
    }
}
