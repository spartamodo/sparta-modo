package com.example.sparta_modo.domain.list;

import com.example.sparta_modo.global.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
}
