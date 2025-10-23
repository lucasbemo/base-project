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

import com.lz.base.project.dataset.entity.TransactionEntity;
import com.lz.base.project.dataset.service.TransactionService;

@RestController
@RequestMapping("/api/dataset/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionEntity> list() { return transactionService.list(); }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> get(@PathVariable Long id) {
        Optional<TransactionEntity> t = transactionService.find(id);
        return t.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionEntity> create(@RequestBody TransactionEntity e) {
        TransactionEntity saved = transactionService.create(e);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionEntity> update(@PathVariable Long id, @RequestBody TransactionEntity e) {
        TransactionEntity updated = transactionService.update(id, e);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/import")
    public ResponseEntity<String> importFromCsv() {
        int imported = transactionService.importFromCsv();
        return ResponseEntity.ok("Imported " + imported + " transactions");
    }
}
