package com.lz.base.project.dataset.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lz.base.project.dataset.entity.CardEntity;
import com.lz.base.project.dataset.port.CardPort;

@Service
public class CardService {

    private final CardPort cardPort;

    public CardService(CardPort cardPort) {
        this.cardPort = cardPort;
    }

    public CardEntity create(CardEntity e) { return cardPort.save(e); }
    public Optional<CardEntity> find(Long id) { return cardPort.findById(id); }
    public List<CardEntity> list() { return cardPort.findAll(); }
    public CardEntity update(Long id, CardEntity e) {
        e.setId(id);
        return cardPort.save(e);
    }
    public void delete(Long id) { cardPort.deleteById(id); }
}
