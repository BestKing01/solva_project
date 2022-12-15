package com.example.demo.repository;

import com.example.demo.model.SpendingLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpendingLimitRep extends JpaRepository<SpendingLimit,Long> {

    Optional<SpendingLimit> findAllById(Long id);

}

