package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.UserWorkspace;
import com.example.sparta_modo.global.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
    @Query("SELECT uw.workspace FROM user_workspace uw WHERE uw.user = :loginUser")
    List<Workspace> findWorkspacesByUser(User loginUser);

    @Query("SELECT uw FROM user_workspace uw JOIN FETCH uw.workspace WHERE uw.user = :loginUser AND uw.workspace.id = :workspaceId")
    UserWorkspace findByWorkspaceIdAndUser(User loginUser,Long workspaceId);
}
