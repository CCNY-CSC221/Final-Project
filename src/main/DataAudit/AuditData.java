package DataAudit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Reviews budget records for duplicate entries, malformed data, and anomalous
 * transaction amounts.
 *
 * @author Raphy Binet Cedeno, Thierno Diallo
 */
public class AuditData {

    private static final double ANOMALY_THRESHOLD = 0.50;

    /**
     * Creates an AuditData object for duplicate and anomaly checks.
     *
     * @author Raphy Binet Cedeno, Thierno Diallo
     */
    public AuditData() {
    }

    /**
     * Checks budget records for exact duplicate entries.
     *
     * @param records the budget records to review
     * @return a list containing records that appear more than once
     * @author Raphy Binet Cedeno, Thierno Diallo
     */
    public List<String> checkForDupes(List<String> records) {
        List<String> duplicates = new ArrayList<>();

        if (records == null || records.isEmpty()) {
            return duplicates;
        }

        Set<String> seenRecords = new HashSet<>();

        for (String record : records) {
            if (record == null) {
                continue;
            }

            String normalizedRecord = record.trim();
            if (normalizedRecord.isEmpty()) {
                continue;
            }

            if (!seenRecords.add(normalizedRecord)) {
                duplicates.add(normalizedRecord);
            }
        }

        return duplicates;
    }

    /**
     * Rechecks raw budget records for missing or unusable values.
     *
     * @param records the raw budget records to inspect
     * @return true if every record is present and non-empty; false otherwise
     * @author Raphy Binet Cedeno, Thierno Diallo
     */
    public boolean recheckData(List<String> records) {
        if (records == null || records.isEmpty()) {
            return false;
        }

        for (String record : records) {
            if (record == null || record.trim().isEmpty()) {
                return false;
            }

            String[] fields = record.split(",", -1);
            if (fields.length != 3) {
                return false;
            }

            String date = fields[0].trim();
            String category = fields[1].trim();
            String amount = fields[2].trim();

            if (date.isEmpty() || category.isEmpty() || amount.isEmpty()) {
                return false;
            }

            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Flags whether a transaction amount should be treated as anomalous.
     *
     * @param amount the transaction amount being reviewed
     * @param typicalAmount the usual amount for comparable records
     * @return true if the anomaly score is high enough to report; false otherwise
     * @author Raphy Binet Cedeno, Thierno Diallo
     */
    public boolean flagAnomaly(double amount, double typicalAmount) {
        return calcAnomalyScore(amount, typicalAmount) > ANOMALY_THRESHOLD;
    }

    /**
     * Calculates how far a transaction amount deviates from a typical amount.
     *
     * @param amount the transaction amount being reviewed
     * @param typicalAmount the usual amount for comparable records
     * @return the absolute deviation ratio used as the anomaly score
     * @author Raphy Binet Cedeno, Thierno Diallo
     */
    public double calcAnomalyScore(double amount, double typicalAmount) {
        double currentAmount = Math.abs(amount);
        double baselineAmount = Math.abs(typicalAmount);

        if (baselineAmount == 0.0) {
            return currentAmount;
        }

        return Math.abs(currentAmount - baselineAmount) / baselineAmount;
    }
}
