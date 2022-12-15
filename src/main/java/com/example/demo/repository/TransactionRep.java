package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface TransactionRep extends JpaRepository<Transaction, Long> {

    LocalDate getOperationTime();

    BigDecimal getAmount();

    List<Transaction> findAllByOperationDateBetween(LocalDate startDate, LocalDate endDate);
}
