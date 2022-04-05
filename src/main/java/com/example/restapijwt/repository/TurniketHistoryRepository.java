package com.example.restapijwt.repository;


import com.example.restapijwt.entity.TurniketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory, Integer> {
}
