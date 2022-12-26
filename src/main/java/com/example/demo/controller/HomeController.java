package com.example.demo.controller;

import com.example.demo.model.SpendingLimit;
import com.example.demo.model.Transaction;
import com.example.demo.repository.SpendingLimitRep;
import com.example.demo.repository.TransactionRep;
import com.example.demo.service.TransactionSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class HomeController {
    private final TransactionSer transactionService;
    @Autowired
    private TransactionRep transactionRep;
    @Autowired
    private SpendingLimitRep spendingLimitRep;
    @Autowired
    public HomeController(TransactionSer transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/qwe1")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        Transaction savedExpense = transactionRep.save(transaction);
        return savedExpense;
    }

    @GetMapping("/qwe")
    public ResponseEntity<List<Transaction>> getExpenses() {
        List<Transaction> transactions = transactionRep.findAll();
        return ResponseEntity.ok(transactions);
    }

    public void markExceededTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            String category = String.valueOf(transaction.getCurrency());
            Double amount = transaction.getAmount();
            Double limit = (double) 0;
            if ("goods".equals(category)) {
                limit = spendingLimitRep.findById(1L).orElse(new SpendingLimit()).getGoodsLimit();
            } else if ("services".equals(category)) {
                limit = spendingLimitRep.findById(1L).orElse(new SpendingLimit()).getServicesLimit();
            }
            if (amount > limit) {
                transaction.setLimitExceeded(true);
                transactionRep.save(transaction);
            }
        }

    }
    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }
    @PutMapping("/limits")
    public void setMonthlyLimit(@RequestParam BigDecimal limit, @RequestParam String currency) {
        transactionService.setMonthlyLimit(limit, currency);
    }

    @GetMapping("/limits")
    public BigDecimal getMonthlyLimit(@RequestParam String currency) {
        return transactionService.getMonthlyLimit(currency);
    }

    @GetMapping("/exceeded")
    public List<Transaction> getTransactionsExceedingLimit(@RequestParam(required = false) String currency) {
        if (currency == null) {
            return transactionService.findTransactionsExceedingLimit(currency);
        } else {
            return transactionService.findTransactionsExceedingLimit(currency);
        }
    }





















}
