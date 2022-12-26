package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_time")
    private LocalDateTime operationTime;

    private double amount;

    private Currency currency;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean limitExceeded;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SpendingLimit spendingLimit;


    // Constructor and getter/setter
}
