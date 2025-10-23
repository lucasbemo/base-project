package com.lz.base.project.dataset.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lz.base.project.dataset.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
