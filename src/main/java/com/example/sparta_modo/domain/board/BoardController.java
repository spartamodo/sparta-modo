package com.example.sparta_modo.domain.board;

import com.example.sparta_modo.domain.board.dto.BoardDto;
import com.example.sparta_modo.global.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDto.ResponseBaseDto> createBoard(@PathVariable Long workspaceId,
                                                                @Valid @ModelAttribute BoardDto.CreateRequest boardDto,
                                                                @AuthenticationPrincipal User user) {

        BoardDto.ResponseBaseDto boardResponse = boardService.createBoard(user, boardDto, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardResponse);
    }

    @GetMapping
    public ResponseEntity<List<BoardDto.ListResponse>> getBoards(@PathVariable Long workspaceId) {
        List<BoardDto.ListResponse> listResponseList = boardService.getBoards(workspaceId);
        return ResponseEntity.status(HttpStatus.OK).body(listResponseList);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto.DetailResponseBaseDto> getDetailBoard(@PathVariable Long workspaceId,
                                                                         @PathVariable Long boardId) {
        BoardDto.DetailResponseBaseDto detailBoard = boardService.getDetailBoard(workspaceId, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(detailBoard);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardDto.DetailResponseBaseDto> updateBoard(@PathVariable Long workspaceId,
                                                                      @PathVariable Long boardId,
                                                                      @Valid @ModelAttribute BoardDto.UpdateRequest request,
                                                                      @AuthenticationPrincipal User user) throws IOException {
        BoardDto.AllDetailResponse allDetailResponse = boardService.updateBoard(user, workspaceId, boardId, request);
        return ResponseEntity.status(HttpStatus.OK).body(allDetailResponse);
    }
}
