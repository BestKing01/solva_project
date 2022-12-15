package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private TransactionRep transactionRep;

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



}
