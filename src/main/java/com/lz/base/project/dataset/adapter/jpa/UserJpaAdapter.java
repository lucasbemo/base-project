package com.lz.base.project.dataset.adapter.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.lz.base.project.dataset.entity.UserEntity;
import com.lz.base.project.dataset.port.UserPort;
import com.lz.base.project.dataset.repository.UserRepository;

@Component
public class UserJpaAdapter implements UserPort {

    private final UserRepository repository;

    public UserJpaAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
