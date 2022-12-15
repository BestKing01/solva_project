package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "monthly_limits")
public class MonthlyLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month_year")
    private LocalDate monthYear;

    @Column(name = "goods_limit")
    private BigDecimal goodsLimit;

    @Column(name = "services_limit")
    private BigDecimal servicesLimit;

    // getters and setters
}
