package com.lz.base.project.dataset.service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lz.base.project.dataset.entity.TransactionEntity;
import com.lz.base.project.dataset.loader.TransactionsCsvLoader;
import com.lz.base.project.dataset.port.TransactionPort;

@Service
public class TransactionService {

    private final TransactionPort transactionPort;
    private final TransactionsCsvLoader transactionsCsvLoader;

    public TransactionService(TransactionPort transactionPort, TransactionsCsvLoader transactionsCsvLoader) {
        this.transactionPort = transactionPort;
        this.transactionsCsvLoader = transactionsCsvLoader;
    }

    public TransactionEntity create(TransactionEntity e) { return transactionPort.save(e); }
    public Optional<TransactionEntity> find(Long id) { return transactionPort.findById(id); }
    public List<TransactionEntity> list() { return transactionPort.findAll(); }
    public TransactionEntity update(Long id, TransactionEntity e) {
        e.setId(id);
        return transactionPort.save(e);
    }
    public void delete(Long id) { transactionPort.deleteById(id); }

    /**
     * Trigger import of transactions from default dataset file.
     * Returns number of records imported.
     */
    public int importFromCsv() {
        Path p = Path.of("dataset/transactions_data.csv");
        return transactionsCsvLoader.loadFromFile(p);
    }
}
