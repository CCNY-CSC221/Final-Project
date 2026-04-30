package DataAudit;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles saving and purging audit reports or audit history records.
 *
 * @author Thierno Diallo
 */
public class StoreAudit {

    private static final String BASE_DIR = "audit_reports";

    /**
     * Initializes the audit directory if it doesn't exist. 
     * It checks for the existance of {@value #BASE_DIR}. If absent, it will create a new directory
     * using {@link java.io.File#mkdirs()}.
     *
     * @author Thierno Diallo & Zeferino Franco Salgado
     */
    public StoreAudit() {
        if (!Files.exists(Paths.get(BASE_DIR))) {
            new java.io.File(BASE_DIR).mkdirs();
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
        return false; // Implementation WIP
    }

    /**
     * Purges audit records for a user within a requested time frame.
     *
     * @param username the username whose audit records should be purged
     * @param timeframe the time frame or retention rule for the purge request
     * @return true if the purge request is valid; false otherwise
     * @author Thierno Diallo
     */
    public boolean purgeRecords(String username, String timeframe) {
        return false; // Implementation WIP
    }
}
