package com.example.restapijwt.repository;


import com.example.restapijwt.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
}
