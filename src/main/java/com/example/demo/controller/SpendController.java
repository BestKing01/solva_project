package com.example.demo.controller;

import com.example.demo.model.SpendingLimit;
import com.example.demo.repository.SpendingLimitRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/spend_limit")
public class SpendController {
    @Autowired
    private SpendingLimitRep spendingLimitRep;

    @GetMapping("/")
    public ResponseEntity<List<SpendingLimit>> getExpenses() {
        List<SpendingLimit> spendingLimits = spendingLimitRep.findAll();
        return ResponseEntity.ok(spendingLimits);
    }

    @GetMapping("/getUserId/{id}")
    public Optional<SpendingLimit> getLimitById(@PathVariable(name = "id") Long id){
        return spendingLimitRep.findById(id);
    }

    @PutMapping("/putUserId/{id}")
    public SpendingLimit updateSpendLimit(@RequestBody SpendingLimit spendingLimit, @PathVariable(name = "id") Long id){
        Optional<SpendingLimit> spendingLimit1 = spendingLimitRep.findAllById(id);

        spendingLimit.setId(id);

        return spendingLimitRep.save(spendingLimit);
    }
    @PostMapping("/createSpend")
    public SpendingLimit createSpend(@RequestBody SpendingLimit spendingLimit) {
        return spendingLimitRep.save(spendingLimit);
    }
}
