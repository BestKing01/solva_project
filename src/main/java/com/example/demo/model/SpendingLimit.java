package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Table(name = "spending_limit")
public class SpendingLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "user_id")
    private Transaction transaction;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double goodsLimit;

    @Column(nullable = false, columnDefinition = "double default 0")
    private double servicesLimit;

    // конструктор без параметров, инициализирующий лимиты нулевыми значениями
    public SpendingLimit() {
        this.goodsLimit = 0.0;
        this.servicesLimit = 0.0;
    }

}
