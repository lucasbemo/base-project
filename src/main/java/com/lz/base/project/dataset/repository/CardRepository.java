package com.lz.base.project.dataset.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lz.base.project.dataset.entity.CardEntity;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
