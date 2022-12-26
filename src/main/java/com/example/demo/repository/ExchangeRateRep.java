package com.example.demo.repository;

import com.example.demo.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
public interface ExchangeRateRep extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByDate(LocalDate date);
    Optional<ExchangeRate> findTopByOrderByDateDesc();
}
