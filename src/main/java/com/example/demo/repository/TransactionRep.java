package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRep extends JpaRepository<Transaction, Long> {
    LocalDate now = LocalDate.now();
    List<Transaction> findByLocalDate(int year, int month);
}
