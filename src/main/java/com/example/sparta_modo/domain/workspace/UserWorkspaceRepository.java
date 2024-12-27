package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.global.entity.User;
import com.example.sparta_modo.global.entity.UserWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
    List<UserWorkspace> findByUser(User loginUser);

    UserWorkspace findByIdAndUser(Long workspaceId, User loginUser);
}
