package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.domain.list.dto.SequenceListDto;
import com.example.sparta_modo.global.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workspaces/{workspaceId}/boards/{boardId}/lists")
@RequiredArgsConstructor
public class SequenceListController {

    private final SequenceListService sequenceListService;

    @PostMapping
    public ResponseEntity<SequenceListDto.Response> createList(@PathVariable Long workspaceId,
                                                               @PathVariable Long boardId,
                                                               @Valid @RequestBody SequenceListDto.Request request,
                                                               @AuthenticationPrincipal User user) {

        SequenceListDto.Response savedList = sequenceListService.createList(user,workspaceId, boardId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedList);
    }

    @PatchMapping("/{listId}/sequence")
    public ResponseEntity<List<SequenceListDto.Response>> updateSequence(@PathVariable Long workspaceId,
                                                                         @PathVariable Long boardId,
                                                                         @PathVariable Long listId,
                                                                         @RequestParam Long moveTo,
                                                                         @AuthenticationPrincipal User user) {

        List<SequenceListDto.Response> sequenceLists = sequenceListService.updateSequence(user,workspaceId, boardId, listId, moveTo);
        return ResponseEntity.status(HttpStatus.OK).body(sequenceLists);
    }
}
