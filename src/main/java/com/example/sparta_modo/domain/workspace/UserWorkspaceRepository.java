package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.global.entity.UserWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
}
