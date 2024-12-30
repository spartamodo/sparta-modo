package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.domain.user.UserRepository;
import com.example.sparta_modo.domain.workspace.dto.UserWorkspaceDto;
import com.example.sparta_modo.domain.workspace.dto.WorkspaceDto;
import com.example.sparta_modo.domain.workspace.dto.WorkspaceInviteDto;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.UserWorkspace;
import com.example.sparta_modo.global.entity.Workspace;
import com.example.sparta_modo.global.entity.enums.Auth;
import com.example.sparta_modo.global.entity.enums.InvitingStatus;
import com.example.sparta_modo.global.entity.enums.Role;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.WorkspaceException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import com.example.sparta_modo.global.exception.errorcode.WorkspaceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceRepository userWorkspaceRepository;

    private final UserRepository userRepository;

    // 워크스페이스 생성
    public WorkspaceDto.Response createWorkspace(User loginUser, WorkspaceDto.Request requestDto) {

        Workspace workspace = requestDto.toEntity();

        workspaceRepository.save(workspace);

        UserWorkspace userWorkspace = UserWorkspace.builder()
                .user(loginUser)
                .workspace(workspace)
                .role(Role.ADMIN)
                .invitingStatus(InvitingStatus.ACCEPTED)
                .build();

        userWorkspaceRepository.save(userWorkspace);

        return WorkspaceDto.Response.toDto(workspace);

    }

    // 워크스페이스 조회
    public List<WorkspaceDto.Response> getWorkspaces(User loginUser) {

        List<Workspace> workspaces = userWorkspaceRepository.findWorkspacesByUser(loginUser);

        if(workspaces.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "해당 사용자의 워크스페이스가 없습니다.");
        }

        return workspaces.stream().map(WorkspaceDto.Response::toDto).toList();
    }

    // 워크스페이스 수정
    @Transactional
    public WorkspaceDto.Response updateWorkspace(User loginUser, Long workspaceId,
                                                 WorkspaceDto.Request requestDto) {
        //권한 검증 및 userWorkspace 반환
        UserWorkspace userWorkspace = forbiddenAccess(loginUser, workspaceId);

        if (userWorkspace == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "사용자의 workspace 를 찾을 수 없습니다.");
        }
        Workspace workspace = userWorkspace.getWorkspace();
        workspace.updateWorkspace(requestDto.getTitle(),requestDto.getDescription());

        return WorkspaceDto.Response.toDto(workspace);
    }

    // 워크스페이스 삭제
    @Transactional
    public void deleteWorkspace(User loginUser, Long workspaceId) {

        if(loginUser.getAuth() != Auth.ADMIN){
            throw new CommonException(ErrorCode.FORBIDDEN_ACCESS, "권한이 없습니다.");
        }

        UserWorkspace userWorkspace = userWorkspaceRepository.findByWorkspaceIdAndUser(loginUser, workspaceId);

        if(userWorkspace == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "사용자의 workspace 를 찾을 수 없습니다.");
        }

        workspaceRepository.deleteById(workspaceId);
    }

    // 워크스페이스 멤버 초대
    @Transactional
    public WorkspaceInviteDto.Response inviteUserWorkspace(User loginUser, WorkspaceInviteDto.Request request, Long workspaceId) {
        // 권한 검증
        forbiddenAccess(loginUser, workspaceId);

        // 멤버 이메일로 멤버찾기
        User user = userRepository.findUserByEmail(request.getEmail());

        // 초대할 워크스페이스
        Workspace workspace = workspaceRepository.findByIdOrElseThrow(workspaceId);

        if(userWorkspaceRepository.existsUserWorkspaceByUserAndWorkspace(user,workspace)){
            throw new CommonException(ErrorCode.ALREADY_EXIST, "해당 사용자는 이미 워크스페이스에 존재합니다.");
        }

        UserWorkspace userWorkspace = UserWorkspace.builder()
                .user(user)
                .workspace(workspace)
                .role(Role.READ_ONLY)
                .invitingStatus(InvitingStatus.INVITING)
                .build();
        userWorkspaceRepository.save(userWorkspace);

        //TODO 알림 기능 추가 ex) loginUser 님이 workspace에 user님을 초대하셨습니다.

        return new WorkspaceInviteDto.Response(workspaceId,user.getId(),user.getEmail());
    }

    // 워크스페이스 멤버 역할 수정
    @Transactional
    public UserWorkspaceDto.Response modifyRole(User loginUser,Long workspaceId, Long userId, UserWorkspaceDto.Request request) {
        // 권한 검증
        forbiddenAccess(loginUser, workspaceId);

        UserWorkspace userWorkspace = userWorkspaceRepository.findByUserIdAndWorkspaceId(userId,workspaceId);

        if(userWorkspace == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "해당 사용자의 워크스페이스를 찾을 수 없습니다.");
        }
        // 변경하려는 역할이 ADMIN 일 경우 예외처리
        if(Role.of(request.getRole()).equals(Role.ADMIN)){
            throw new WorkspaceException(WorkspaceErrorCode.IMPOSSIBLE_MODIFY);
        }
        // 역할 수정
        userWorkspace.updateRole(request.getRole());

        return new UserWorkspaceDto.Response(workspaceId,userId,userWorkspace.getRole());
    }

    // 워크스페이스 초대 조회
    public List<UserWorkspaceDto.Response> getInviteWorkspace(User loginUser) {

        List<UserWorkspace> userWorkspaces = userWorkspaceRepository.findByUserAndInvitingStatus(loginUser,InvitingStatus.INVITING);

        if(userWorkspaces.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "사용자에게 수신된 초대를 찾을 수 없습니다.");
        }

        return userWorkspaces.stream().map(UserWorkspaceDto.Response::toDto).toList();
    }

    // 워크스페이스 초대 수락, 거절
    @Transactional
    public boolean acceptInviting(User loginUser, Long workspaceId,Boolean decide) {
        UserWorkspace userWorkspace = userWorkspaceRepository.findByWorkspaceIdAndUser(loginUser, workspaceId);
        if(userWorkspace == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, "사용자의 workspace 를 찾을 수 없습니다.");
        }
        if(userWorkspace.getInvitingStatus() != InvitingStatus.INVITING){
            throw new WorkspaceException(WorkspaceErrorCode.ALREADY_DECIDED_ACCESS);
        }
        //초대 수락
        if(decide) {
            userWorkspace.acceptInvitingStatus();
            return true;
        }
        // 초대 거절시 삭제
        userWorkspaceRepository.delete(userWorkspace);
        return false;

    }

    public UserWorkspace forbiddenAccess(User loginUser, Long workspaceId) {
        UserWorkspace adminUserWorkspace = userWorkspaceRepository.findByWorkspaceIdAndUser(loginUser, workspaceId);
        if(adminUserWorkspace.getRole() != Role.ADMIN && adminUserWorkspace.getRole() != Role.MANAGER) {
            throw new CommonException(ErrorCode.FORBIDDEN_ACCESS, "권한이 없습니다.");
        }

        return adminUserWorkspace;
    }
}
