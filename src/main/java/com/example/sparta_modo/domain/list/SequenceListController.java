package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.domain.list.dto.SequenceListDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards/{boardId}/lists")
@RequiredArgsConstructor
public class SequenceListController {

    private final SequenceListService listService;

    @PostMapping
    public ResponseEntity<SequenceListDto.Response> createList(@PathVariable Long boardId,
                                                               @Valid @RequestBody SequenceListDto.Request request) {

        SequenceListDto.Response savedList = listService.createList(boardId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedList);
    }

    @PatchMapping("/{listId}")
    public ResponseEntity<List<SequenceListDto.Response>> updateSequence(@PathVariable Long boardId,
                                            @PathVariable Long listId,
                                            @RequestParam Long moveTo) {

        List<SequenceListDto.Response> sequenceLists = listService.updateSequence(boardId, listId, moveTo);
        return ResponseEntity.status(HttpStatus.OK).body(sequenceLists);
    }
}
