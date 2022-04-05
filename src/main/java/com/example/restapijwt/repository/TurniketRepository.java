package com.example.restapijwt.repository;


import com.example.restapijwt.entity.Turniket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurniketRepository extends JpaRepository<Turniket, Integer> {
}
