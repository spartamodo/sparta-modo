package com.example.sparta_modo.domain.workspace;

import com.example.sparta_modo.global.entity.Workspace;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    default Workspace findByIdOrElseThrow(Long workspaceId){
        return findById(workspaceId)
                .orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_VALUE, "워크스페이스를 찾을 수 없습니다."));
    }
}
