package com.example.sparta_modo.domain.card;


import com.example.sparta_modo.global.entity.CardHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardHistoryRepository extends JpaRepository<CardHistory, Long> {
}
