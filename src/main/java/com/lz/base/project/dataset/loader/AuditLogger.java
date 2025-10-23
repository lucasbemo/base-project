package com.lz.base.project.dataset.loader;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Audit logger: emits structured audit messages via SLF4J so the logging backend (Logback/Log4j2)
 * can control destination (console, file, external system). This follows logging best practices.
 */
public final class AuditLogger {

    private static final Logger AUDIT = LoggerFactory.getLogger("audit");
    private static final Marker AUDIT_MARKER = MarkerFactory.getMarker("AUDIT");
    private static final DateTimeFormatter TF = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    private AuditLogger() {}

    public static void log(String loaderName, Instant start, Instant end, int itemsSaved) {
        String startS = TF.format(start);
        String endS = TF.format(end);
        long durationMs = Duration.between(start, end).toMillis();
        // structured key=value style so it's easy to parse
        String msg = String.format("loader=%s start=%s end=%s durationMs=%d saved=%d",
                loaderName, startS, endS, durationMs, itemsSaved);
        AUDIT.info(AUDIT_MARKER, msg);
    }
}
