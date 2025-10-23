package com.lz.base.project.dataset.port;

import java.util.List;
import java.util.Optional;

import com.lz.base.project.dataset.entity.TransactionEntity;

public interface TransactionPort {
    TransactionEntity save(TransactionEntity t);
    Optional<TransactionEntity> findById(Long id);
    List<TransactionEntity> findAll();
    void deleteById(Long id);
}
