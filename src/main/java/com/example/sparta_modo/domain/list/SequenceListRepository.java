package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.domain.list.dto.SequenceListDto;
import com.example.sparta_modo.global.entity.Board;
import com.example.sparta_modo.global.entity.SequenceList;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SequenceListRepository extends JpaRepository<SequenceList, Long> {
    default SequenceList findListByIdOrElseThrow(Long listId) {
        return findListById(listId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VALUE, "해당 리스트를 찾을 수 없음"));
    }

    Optional<SequenceList> findListById(Long id);

    @Query(value = "SELECT new com.example.sparta_modo.domain.list.dto.SequenceListDto$Response(sl.id, sl.title) " +
            "FROM sequence_list sl where sl.board = :board order by sl.priority")
    List<SequenceListDto.Response> orderdList(@Param("board") Board findBoard);

}
