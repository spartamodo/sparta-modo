package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.domain.workspace.dto.WorkspaceDto;
import com.example.sparta_modo.global.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    // 워크스페이스 생성
    @PostMapping
    public ResponseEntity<WorkspaceDto.Response> createWorkspace(
            @RequestBody WorkspaceDto.Request requestDto,
            HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession(false);
        User loginUser = (User) session.getAttribute("loginUser");

        WorkspaceDto.Response response = workspaceService.createWorkspace(loginUser, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 워크스페이스 조회

    // 워크스페이스 수정

    // 워크스페이스 삭제

    // 워크스페이스 멤버 초대

    // 워크스페이스 멤버 초대 수락

    // 멤버 역할 수정
}

