package com.lz.base.project.dataset.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lz.base.project.dataset.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
