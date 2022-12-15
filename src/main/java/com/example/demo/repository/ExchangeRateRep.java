package com.example.demo.repository;

import com.example.demo.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface ExchangeRateRep extends JpaRepository<ExchangeRate, Long> {


    Optional<ExchangeRate> findById(LocalDate rateDate);

    BigDecimal getRate();
}
