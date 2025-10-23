package com.lz.base.project.dataset.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lz.base.project.dataset.entity.CardEntity;
import com.lz.base.project.dataset.service.CardService;

@RestController
@RequestMapping("/api/dataset/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardEntity> list() { return cardService.list(); }

    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> get(@PathVariable Long id) {
        Optional<CardEntity> c = cardService.find(id);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardEntity> create(@RequestBody CardEntity e) {
        CardEntity saved = cardService.create(e);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardEntity> update(@PathVariable Long id, @RequestBody CardEntity e) {
        CardEntity updated = cardService.update(id, e);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
