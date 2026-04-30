package DataAudit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Handles saving and purging audit reports or audit history records.
 *
 * @author Thierno Diallo
 */
public class StoreAudit {

    private static final String BASE_DIR = "audit_reports";

    /**
     * Initializes the audit directory if it doesn't exist. 
     * Uses {@link Files#createDirectories(Path)} to create {@link Files#createDirectories(Path)} as well
     * as the nevessary parent directories. 
     * using {@link java.io.File#mkdirs()}.
     *
     * @author Thierno Diallo & Zeferino Franco Salgado
     */
    public StoreAudit() {
        try {
            Files.createDirectories(Paths.get(BASE_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create audit directory: " + e.getMessage(), e);
        }
    }

    /**
     * Saves the generated audit report for a user and year.
     *
     * @paraim username the username associated with the audit
     * @param year the calendar year selected for audit
     * @param report the audit report content to save
     * @return true if the audit report input is valid for saving; false otherwise
     * @author Thierno Diallo
     */
    public boolean saveAudit(String username, int year, String report) {
        if (username == null || username.trim().isEmpty() || report == null) {
            return false;
        }
        if (year < 1900 || year > 2100) {
            return false;
        }

        String fileName = String.format("%s/%s_%d_audit.txt", BASE_DIR, username, year);
        Path path = Paths.get(fileName);

        // Write header and content
        String fileName = String.format("%s/%s_%d_audit.txt", BASE_DIR, username, year);
        Path path = Paths.get(fileName);

        List<String> lines = List.of(
                "Audit Report - " + LocalDateTime.now(),
                "User: " + username,
                "Year: " + year,
                "Report:",
                report
                );

        try {
            Files.write(path, lines);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving audit report: " + e.getMessage());
            return false;
        }
    }

    /**
     * Purges audit records for a user within a requested time frame.
     *
     * This method deletes files matching the pattern {@code username_*_timeframe*.txt}
     * in the configured base directory. It returns {@code true} if at least one file was
     * successfully deleted, {@code false} otherwise. No exception is thrown for deletion failures.
     *
     * @param username the username whose audit records should be purged; must not be null or blank
     * @param timeframe the time frame or retention rule for the purge request; must not be null
     * @return {@code true} if at least one file was successfully deleted; {@code false}
     * if no matching files were found, deletion failed, or input validation failed.
     *
     * @author Thierno Diallo & Zeferino Franco Salgado
     */
    public boolean purgeRecords(String username, String timeframe) {
        if (username == null || username.trim().isEmpty() || timeframe == null) {
            return false;
        }

        File dir = new File(BASE_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }

        boolean deletedAny = false;

        String regex = Pattern.quote(username) + ".*_" + Pattern.quote(timeframe) + ".*\\.txt";
        Pattern pattern = Pattern.compile(regex);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (pattern.matcher(file.getName()).matches()) {
                    try {
                        Files.deleteIfExists(file.toPath());
                        deletedAny = true;
                    } catch (IOException e) {
                        System.err.println("Failed to delete file: " + file.getName() + " - " + e.getMessage());
                    }
                }
            }
        }

        return deletedAny;
    }
}
