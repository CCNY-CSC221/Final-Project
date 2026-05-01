import java.util.List;

/**
 * Coordinates audit history checks and records the status of audit activity.
 *
 * @author Yeasin Arafat Riyad
 */
public class AuditControl {
    
    /** Threshold percentage for detecting anomalies (50% deviation) */
    private static final double DEVIATION_THRESHOLD = 0.50;
    
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
     * Uses statistical analysis to detect anomalies:
     * - Calculates the average (mean) of historical amounts
     * - Checks if current amount deviates more than 50% from the average
     * - Handles edge cases (empty history, zero amounts)
     *
     * @param historicalAmounts previous amounts from the same category or record type
     * @param currentAmount the current amount being compared against history
     * @return true if the current amount deviates significantly; false otherwise
     * @author Yeasin Arafat Riyad
     */
    public boolean checkHistory(List<Double> historicalAmounts, double currentAmount) {
        // Edge case: No historical data to compare against
        if (historicalAmounts == null || historicalAmounts.isEmpty()) {
            return false; // Cannot determine deviation without history
        }
        
        // Calculate average of historical amounts
        double sum = 0.0;
        int count = 0;
        
        for (Double amount : historicalAmounts) {
            if (amount != null) {
                sum += Math.abs(amount); // Use absolute value for expenses (negative)
                count++;
            }
        }
        
        // Edge case: All amounts were null
        if (count == 0) {
            return false;
        }
        
        double average = sum / count;
        
        // Edge case: Average is zero (free items, zero-cost transactions)
        if (average == 0) {
            return Math.abs(currentAmount) > 0; // Any non-zero amount is unusual
        }
        
        // Calculate deviation percentage
        double currentAbsolute = Math.abs(currentAmount);
        double deviation = Math.abs(currentAbsolute - average) / average;
        
        // Check if deviation exceeds threshold (50%)
        return deviation > DEVIATION_THRESHOLD;
    }
    
    /**
     * Builds a status message for a completed or incomplete audit run.
     * 
     * Generates human-readable status messages for:
     * - Successfully completed audits
     * - Incomplete or failed audits
     * - Includes username and year for context
     *
     * @param username the username associated with the audit
     * @param year the calendar year selected for audit
     * @param auditCompleted true if the audit completed; false if it stopped early
     * @return a status message describing the audit result
     * @author Yeasin Arafat Riyad
     */
    public String checkStatus(String username, int year, boolean auditCompleted) {
        // Validate inputs
        if (username == null || username.trim().isEmpty()) {
            return "Error: Invalid username provided for audit status";
        }
        
        if (year < 1900 || year > 2100) {
            return "Error: Invalid year provided for audit status";
        }
        
        // Build status message based on completion
        if (auditCompleted) {
            return String.format(
                "Audit completed successfully for user '%s' (year %d)", 
                username, 
                year
            );
        } else {
            return String.format(
                "Audit incomplete for user '%s' (year %d) - please review for errors", 
                username, 
                year
            );
        }
    }
}

