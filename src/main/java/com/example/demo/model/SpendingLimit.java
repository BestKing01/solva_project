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

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "goods_limit")
    private Double goodsLimit;

    @Column(name = "services_limit")
    private Double servicesLimit;

    // конструктор без параметров, инициализирующий лимиты нулевыми значениями
    public SpendingLimit() {
        this.goodsLimit = 0.0;
        this.servicesLimit = 0.0;
    }

}
