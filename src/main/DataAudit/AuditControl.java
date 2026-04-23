package DataAudit;

import java.util.List;

/**
 * Coordinates audit history checks and records the status of audit activity.
 *
 * @author Yeasin Arafat Riyad
 */
public class AuditControl {

    /**
     * Creates an AuditControl object for audit workflow checks.
     *
     * @author Yeasin Arafat Riyad
     */
    public AuditControl() {
    }

    /**
     * Reviews past transaction amounts to determine whether a current amount
     * deviates significantly from historical behavior.
     *
     * @param historicalAmounts previous amounts from the same category or record type
     * @param currentAmount the current amount being compared against history
     * @return true if the current amount deviates significantly; false otherwise
     * @author Yeasin Arafat Riyad
     */
    public boolean checkHistory(List<Double> historicalAmounts, double currentAmount) {
        return false; // Implementation WIP
    }

    /**
     * Builds a status message for a completed or incomplete audit run.
     *
     * @param username the username associated with the audit
     * @param year the calendar year selected for audit
     * @param auditCompleted true if the audit completed; false if it stopped early
     * @return a status message describing the audit result
     * @author Yeasin Arafat Riyad
     */
    public String checkStatus(String username, int year, boolean auditCompleted) {
        return ""; // Implementation WIP
    }
}
