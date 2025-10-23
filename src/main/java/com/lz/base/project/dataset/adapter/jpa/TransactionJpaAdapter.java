package com.lz.base.project.dataset.adapter.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.lz.base.project.dataset.entity.TransactionEntity;
import com.lz.base.project.dataset.port.TransactionPort;
import com.lz.base.project.dataset.repository.TransactionRepository;

@Component
public class TransactionJpaAdapter implements TransactionPort {

    private final TransactionRepository repository;

    public TransactionJpaAdapter(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionEntity save(TransactionEntity t) { return repository.save(t); }

    @Override
    public Optional<TransactionEntity> findById(Long id) { return repository.findById(id); }

    @Override
    public List<TransactionEntity> findAll() { return repository.findAll(); }

    @Override
    public void deleteById(Long id) { repository.deleteById(id); }
}
