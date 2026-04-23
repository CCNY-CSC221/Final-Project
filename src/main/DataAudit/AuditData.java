package DataAudit;

import java.util.List;

/**
 * Reviews budget records for duplicate entries, malformed data, and anomalous
 * transaction amounts.
 *
 * @author Raphy Binet Cedeno
 */
public class AuditData {

    /**
     * Creates an AuditData object for duplicate and anomaly checks.
     *
     * @author Raphy Binet Cedeno
     */
    public AuditData() {
    }

    /**
     * Checks budget records for exact duplicate entries.
     *
     * @param records the budget records to review
     * @return a list containing records that appear more than once
     * @author Raphy Binet Cedeno
     */
    public List<String> checkForDupes(List<String> records) {
        return null; // Implementation WIP
    }

    /**
     * Rechecks raw budget records for missing or unusable values.
     *
     * @param records the raw budget records to inspect
     * @return true if every record is present and non-empty; false otherwise
     * @author Raphy Binet Cedeno
     */
    public boolean recheckData(List<String> records) {
        return false; // Implementation WIP
    }

    /**
     * Flags whether a transaction amount should be treated as anomalous.
     *
     * @param amount the transaction amount being reviewed
     * @param typicalAmount the usual amount for comparable records
     * @return true if the anomaly score is high enough to report; false otherwise
     * @author Raphy Binet Cedeno
     */
    public boolean flagAnomaly(double amount, double typicalAmount) {
        return false; // Implementation WIP
    }

    /**
     * Calculates how far a transaction amount deviates from a typical amount.
     *
     * @param amount the transaction amount being reviewed
     * @param typicalAmount the usual amount for comparable records
     * @return the absolute deviation ratio used as the anomaly score
     * @author Raphy Binet Cedeno
     */
    public double calcAnomalyScore(double amount, double typicalAmount) {
        return 0.0; // Implementation WIP
    }
}
