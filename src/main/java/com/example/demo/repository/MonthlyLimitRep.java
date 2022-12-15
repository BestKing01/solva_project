package com.example.demo.repository;

import com.example.demo.model.MonthlyLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface MonthlyLimitRep extends JpaRepository<MonthlyLimit, Long> {

    Optional<MonthlyLimit> findById(LocalDate monthYear);

    BigDecimal getGoodsLimit();

    BigDecimal getServicesLimit();
}
