package com.example.sparta_modo.domain.card.file;

import com.example.sparta_modo.global.entity.Card;
import com.example.sparta_modo.global.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    // 특정 카드 ID에 연결된 파일 조회
    List<File> findByCard(Card card);

    // 특정 파일 ID로 존재 여부 확인
    boolean existsById(Long fileId);
}

