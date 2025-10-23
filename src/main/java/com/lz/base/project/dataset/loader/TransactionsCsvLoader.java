package com.lz.base.project.dataset.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lz.base.project.dataset.entity.TransactionEntity;
import com.lz.base.project.dataset.port.TransactionPort;

@Component
public class TransactionsCsvLoader implements CommandLineRunner {

    private final TransactionPort transactionPort;
    private static final Logger LOG = LoggerFactory.getLogger(TransactionsCsvLoader.class);

    public TransactionsCsvLoader(TransactionPort transactionPort) {
        this.transactionPort = transactionPort;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("TransactionsCsvLoader started.");
        Path p = Path.of("dataset/transactions_data.csv");
        if (!Files.exists(p)) return;
        java.time.Instant start = java.time.Instant.now();
        int saved = loadFromFile(p);
        java.time.Instant end = java.time.Instant.now();
        AuditLogger.log("TransactionsCsvLoader", start, end, saved);
        LOG.info("TransactionsCsvLoader finished: saved={} durationMs={}", saved, java.time.Duration.between(start, end).toMillis());
    }

    /**
     * Load transactions from CSV file at path p. Returns number of records imported.
     */
    public int loadFromFile(Path p) {
        if (p == null || !Files.exists(p)) return 0;
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            br.readLine(); // header
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = CsvUtils.parseCsvLine(line);
                if (cols.length < 11) continue;
                TransactionEntity t = new TransactionEntity();
                t.setId(CsvUtils.parseLong(cols[0]));
                t.setDate(CsvUtils.trimQuotes(cols[1]));
                t.setClientId(CsvUtils.parseLong(cols[2]));
                t.setCardId(CsvUtils.parseLong(cols[3]));
                t.setAmount(CsvUtils.parseMoney(cols[4]));
                t.setUseChip(CsvUtils.trimQuotes(cols[5]));
                t.setMerchantId(CsvUtils.parseLong(cols[6]));
                t.setMerchantCity(CsvUtils.trimQuotes(cols[7]));
                t.setMerchantState(CsvUtils.trimQuotes(cols[8]));
                t.setZip(CsvUtils.trimQuotes(cols[9]));
                t.setMcc(CsvUtils.trimQuotes(cols[10]));
                if (cols.length > 11) t.setErrors(CsvUtils.trimQuotes(cols[11]));
                transactionPort.save(t);
                count++;
            }
        } catch (IOException e) {
            // ignore loader failures
        }
        return count;
    }
}
