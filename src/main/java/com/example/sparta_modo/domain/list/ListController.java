package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.domain.list.dto.ListDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{boardId}/lists")
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    @PostMapping
    public ResponseEntity<ListDto.Response> createList(@PathVariable Long boardId,
                                        @Valid @RequestBody ListDto.Request request) {

        ListDto.Response savedList = listService.createList(boardId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedList);
    }
}
