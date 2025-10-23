package com.lz.base.project.dataset.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lz.base.project.dataset.entity.UserEntity;
import com.lz.base.project.dataset.port.UserPort;

@Component
public class UsersCsvLoader implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(UsersCsvLoader.class);

    private final UserPort userPort;

    public UsersCsvLoader(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("UsersCsvLoader started.");
        Path p = Path.of("dataset/users_data.csv");
        if (!Files.exists(p)) return;
        java.time.Instant start = java.time.Instant.now();
        int saved = loadFromFile(p);
        java.time.Instant end = java.time.Instant.now();
        AuditLogger.log("UsersCsvLoader", start, end, saved);
        LOG.info("UsersCsvLoader finished: saved={} durationMs={}", saved, java.time.Duration.between(start, end).toMillis());
    }

    /**
     * Public method to load users from a CSV file path. Returns the number of records imported.
     */
    public int loadFromFile(Path p) {
        if (p == null || !Files.exists(p)) return 0;
        int count = 0;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            // consume header line if present
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = CsvUtils.parseCsvLine(line);
                if (cols.length < 13) continue;
                UserEntity u = new UserEntity();
                u.setId(CsvUtils.parseLong(cols[0]));
                u.setCurrentAge(CsvUtils.parseInt(cols[1]));
                u.setRetirementAge(CsvUtils.parseInt(cols[2]));
                u.setBirthYear(CsvUtils.parseInt(cols[3]));
                u.setBirthMonth(CsvUtils.parseInt(cols[4]));
                u.setGender(CsvUtils.trimQuotes(cols[5]));
                u.setAddress(CsvUtils.trimQuotes(cols[6]));
                u.setLatitude(CsvUtils.parseDouble(cols[7]));
                u.setLongitude(CsvUtils.parseDouble(cols[8]));
                u.setPerCapitaIncome(CsvUtils.parseMoney(cols[9]));
                u.setYearlyIncome(CsvUtils.parseMoney(cols[10]));
                u.setTotalDebt(CsvUtils.parseMoney(cols[11]));
                u.setCreditScore(CsvUtils.parseInt(cols[12]));
                if (cols.length > 13) u.setNumCreditCards(CsvUtils.parseInt(cols[13]));
                userPort.save(u);
                count++;
            }
        } catch (IOException e) {
            // ignore loader failures during startup or manual import
        }
        return count;
    }

    // parsing helpers moved to CsvUtils
}
