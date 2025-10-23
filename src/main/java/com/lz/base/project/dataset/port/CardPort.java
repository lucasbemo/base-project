package com.lz.base.project.dataset.port;

import java.util.List;
import java.util.Optional;

import com.lz.base.project.dataset.entity.CardEntity;

public interface CardPort {
    CardEntity save(CardEntity card);
    Optional<CardEntity> findById(Long id);
    List<CardEntity> findAll();
    void deleteById(Long id);
}
