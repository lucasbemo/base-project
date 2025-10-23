package com.lz.base.project.dataset.adapter.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.lz.base.project.dataset.entity.CardEntity;
import com.lz.base.project.dataset.port.CardPort;
import com.lz.base.project.dataset.repository.CardRepository;

@Component
public class CardJpaAdapter implements CardPort {

    private final CardRepository repository;

    public CardJpaAdapter(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public CardEntity save(CardEntity card) { return repository.save(card); }

    @Override
    public Optional<CardEntity> findById(Long id) { return repository.findById(id); }

    @Override
    public List<CardEntity> findAll() { return repository.findAll(); }

    @Override
    public void deleteById(Long id) { repository.deleteById(id); }
}
