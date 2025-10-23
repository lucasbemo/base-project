package com.lz.base.project.dataset.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {

    private CsvUtils() {}

    public static String trimQuotes(String s) {
        if (s == null) return null;
        return s.replaceAll("^\"|\"$", "").trim();
    }

    @SuppressWarnings("UnnecessaryTemporaryOnConversionFromString")
    public static Long parseLong(String s) {
        try {
            String t = trimQuotes(s);
            if (t == null || t.isEmpty()) return null;
            return Long.parseLong(t);
        } catch (NumberFormatException e) { return null; }
    }

    @SuppressWarnings({"UnnecessaryTemporaryOnConversionFromString", "UseSpecificCatch"})
    public static Integer parseInt(String s) {
        try {
            String t = trimQuotes(s).replaceAll("[^0-9-]", "");
            if (t.isEmpty()) return null;
            return Integer.parseInt(t);
        } catch (Exception e) { return null; }
    }

    @SuppressWarnings({"UnnecessaryTemporaryOnConversionFromString", "UseSpecificCatch"})
    public static Double parseDouble(String s) {
        try {
            String t = trimQuotes(s);
            if (t == null || t.isEmpty()) return null;
            return Double.parseDouble(t);
        } catch (Exception e) { return null; }
    }

    @SuppressWarnings("UnnecessaryTemporaryOnConversionFromString")
    public static BigDecimal parseMoney(String s) {
        try {
            String t = trimQuotes(s).replaceAll("[^0-9.-]", "");
            if (t.isEmpty()) return null;
            return new BigDecimal(t);
        } catch (Exception e) { return null; }
    }

    @SuppressWarnings("CollectionsToArray")
    public static String[] parseCsvLine(String line) {
        if (line == null) return new String[0];
        List<String> cols = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                cols.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        cols.add(cur.toString());
        return cols.toArray(new String[0]);
    }
}
