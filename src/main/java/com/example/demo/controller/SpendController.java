package com.example.demo.controller;

import com.example.demo.model.SpendingLimit;
import com.example.demo.repository.SpendingLimitRep;
import lombok.Lombok;
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
        spendingLimit.setId(id);

        return spendingLimitRep.save(spendingLimit);
    }
    @PostMapping("/createSpend")
    public SpendingLimit createSpend(@RequestBody SpendingLimit spendingLimit) {
        return spendingLimitRep.save(spendingLimit);
    }
    @GetMapping("getGoodsLimit/{id}")
    public double getLimit(@PathVariable(name = "id") Long id){
        SpendingLimit spendingLimit = spendingLimitRep.findById(id).orElse(null);
        return spendingLimit != null ? spendingLimit.getGoodsLimit() : 0;
    }

    @GetMapping("getServicesLimit/{id}")
    public Double getServicesLimit(@PathVariable(name = "id") Long id) {
        SpendingLimit spendingLimit = spendingLimitRep.findById(id).orElse(null);
        return spendingLimit != null ? spendingLimit.getServicesLimit() : 0 ;
    }
}
