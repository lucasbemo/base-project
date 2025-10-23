package com.lz.base.project.dataset.port;

import java.util.List;
import java.util.Optional;

import com.lz.base.project.dataset.entity.UserEntity;

public interface UserPort {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAll();
    void deleteById(Long id);
}
