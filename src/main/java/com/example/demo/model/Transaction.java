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

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String category;

    private boolean exceeded;
    // Constructor and getter/setter
}
