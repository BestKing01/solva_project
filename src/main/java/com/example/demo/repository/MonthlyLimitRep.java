package com.example.demo.repository;

import com.example.demo.model.MonthlyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MonthlyLimitRep extends JpaRepository<MonthlyLimit, Long> {
    Optional<MonthlyLimit> findByCurrency(String currency);
}
