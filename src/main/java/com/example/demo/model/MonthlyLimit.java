package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "limit", nullable = false)
    private BigDecimal limit;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    public MonthlyLimit(String currency, BigDecimal limit, LocalDate now) {
        this.currency = currency;
        this.limit = limit;
        this.startDate = now;
    }
}