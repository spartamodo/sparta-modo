package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.global.dto.MsgDto;
import com.example.sparta_modo.domain.workspace.dto.UserWorkspaceDto;
import com.example.sparta_modo.domain.workspace.dto.WorkspaceDto;
import com.example.sparta_modo.domain.workspace.dto.WorkspaceInviteDto;
import com.example.sparta_modo.global.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final UserWorkspaceRepository userWorkspaceRepository;

    // 워크스페이스 생성
    @PostMapping
    public ResponseEntity<WorkspaceDto.Response> createWorkspace(
            @RequestBody WorkspaceDto.Request requestDto,
            @AuthenticationPrincipal User loginUser){


        WorkspaceDto.Response response = workspaceService.createWorkspace(loginUser, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 워크스페이스 조회
    @GetMapping
    public ResponseEntity<List<WorkspaceDto.Response>> getWorkspace(
            @AuthenticationPrincipal User loginUser){

        return ResponseEntity.status(HttpStatus.OK).body(workspaceService.getWorkspaces(loginUser));
    }

    // 워크스페이스 수정
    @PatchMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDto.Response> updateWorkspace(
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceDto.Request requestDto,
            @AuthenticationPrincipal User loginUser){

        return ResponseEntity.status(HttpStatus.OK).body(workspaceService.updateWorkspace(loginUser,workspaceId,requestDto));
    }
    // 워크스페이스 삭제
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<MsgDto> deleteWorkspace(
            @PathVariable Long workspaceId,
            @AuthenticationPrincipal User loginUser
    ){
        workspaceService.deleteWorkspace(loginUser,workspaceId);

        return ResponseEntity.status(HttpStatus.OK).body(new MsgDto("워크스페이스 삭제 완료"));
    }


    // 워크스페이스 멤버 초대
    @PostMapping("/{workspaceId}/users")
    public ResponseEntity<WorkspaceInviteDto.Response> inviteUser(
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceInviteDto.Request request,
            @AuthenticationPrincipal User loginUser
    ){
        WorkspaceInviteDto.Response response = workspaceService.inviteUserWorkspace(loginUser,request,workspaceId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 멤버 역할 수정
    @PatchMapping("/{workspaceId}/users/{userId}/roles")
    public ResponseEntity<UserWorkspaceDto.Response> modifyRole(
            @PathVariable Long workspaceId,
            @PathVariable Long userId,
            @RequestBody UserWorkspaceDto.Request request,
            @AuthenticationPrincipal User loginUser
    ){
        UserWorkspaceDto.Response response = workspaceService.modifyRole(loginUser, workspaceId,userId,request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

