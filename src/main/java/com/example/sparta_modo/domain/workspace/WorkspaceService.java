package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.domain.workspace.dto.WorkspaceDto;
import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.UserWorkspace;
import com.example.sparta_modo.global.entity.Workspace;
import com.example.sparta_modo.global.entity.enums.Auth;
import com.example.sparta_modo.global.entity.enums.InvitingStatus;
import com.example.sparta_modo.global.entity.enums.Role;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserWorkspaceRepository userWorkspaceRepository;

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

        if(loginUser.getAuth() != Auth.ADMIN){
            throw new CommonException(ErrorCode.FORBIDDEN_ACCESS,"ADMIN 이 아닙니다.");
        }
        List<UserWorkspace> userWorkspaces = userWorkspaceRepository.findByUser(loginUser);

        List<Workspace> workspaces = userWorkspaces.stream().map(UserWorkspace::getWorkspace).toList();

        return workspaces.stream().map(WorkspaceDto.Response::toDto).toList();
    }

    // 워크스페이스 수정
    public WorkspaceDto.Response updateWorkspace(User loginUser, Long workspaceId,
                                                 WorkspaceDto.Request requestDto) {

        UserWorkspace userWorkspace = userWorkspaceRepository.findByIdAndUser(workspaceId, loginUser);

        if(userWorkspace == null){
            throw new CommonException(ErrorCode.NOT_FOUND_VALUE, " workspace 를 찾을 수 없습니다.");
        }

        Workspace workspace = userWorkspace.getWorkspace();

        workspace.updateWorkspace(requestDto.getTitle(),requestDto.getDescription());

        workspaceRepository.save(workspace);

        return WorkspaceDto.Response.toDto(workspace);
    }
}
