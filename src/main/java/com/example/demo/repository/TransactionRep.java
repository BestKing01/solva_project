package com.example.demo.repository;

import com.example.demo.model.SpendingLimit;
import com.example.demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRep extends JpaRepository<Transaction, Long> {

    List<Transaction> findByLimitExceeded(boolean limitExceeded);
    @Query("SELECT t FROM Transaction t WHERE t.limitExceeded = true")
    List<Transaction> findTransactionsExceedingLimit();

}
