package com.lz.base.project.dataset.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lz.base.project.dataset.entity.CardEntity;
import com.lz.base.project.dataset.port.CardPort;

@Component
public class CardsCsvLoader implements CommandLineRunner {

    private final CardPort cardPort;
    private static final Logger LOG = LoggerFactory.getLogger(CardsCsvLoader.class);

    public CardsCsvLoader(CardPort cardPort) {
        this.cardPort = cardPort;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("CardsCsvLoader started.");
        Path p = Path.of("dataset/cards_data.csv");
        if (!Files.exists(p)) return;
        java.time.Instant start = java.time.Instant.now();
        int saved = loadFromFile(p);
        java.time.Instant end = java.time.Instant.now();
        AuditLogger.log("CardsCsvLoader", start, end, saved);
        LOG.info("CardsCsvLoader finished: saved={} durationMs={}", saved, java.time.Duration.between(start, end).toMillis());
    }

    public int loadFromFile(Path p) {
        if (p == null || !Files.exists(p)) return 0;
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            br.readLine(); // header
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = CsvUtils.parseCsvLine(line);
                if (cols.length < 13) continue;
                CardEntity c = new CardEntity();
                c.setId(CsvUtils.parseLong(cols[0]));
                c.setClientId(CsvUtils.parseLong(cols[1]));
                c.setCardBrand(CsvUtils.trimQuotes(cols[2]));
                c.setCardType(CsvUtils.trimQuotes(cols[3]));
                c.setCardNumber(CsvUtils.trimQuotes(cols[4]));
                c.setExpires(CsvUtils.trimQuotes(cols[5]));
                c.setCvv(CsvUtils.trimQuotes(cols[6]));
                c.setHasChip(CsvUtils.trimQuotes(cols[7]));
                c.setNumCardsIssued(CsvUtils.parseInt(cols[8]));
                c.setCreditLimit(CsvUtils.parseMoney(cols[9]));
                c.setAcctOpenDate(CsvUtils.trimQuotes(cols[10]));
                c.setYearPinLastChanged(CsvUtils.parseInt(cols[11]));
                c.setCardOnDarkWeb(CsvUtils.trimQuotes(cols[12]));
                cardPort.save(c);
                count++;
            }
        } catch (IOException e) {
            // ignore
        }
        return count;
    }
}
