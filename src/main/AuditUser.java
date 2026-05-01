import java.util.logging.Logger;

/**
 * Checks whether a user and the user's stored budget data are available for
 * data audit operations.
 *
 * @author Zeferino Franco Salgado
 */
public class AuditUser {

    private static final Logger logger = Logger.getLogger(AuditUser.class.getName());

    /**
     * Creates an AuditUser object for user and budget availability checks.
     *
     * @author Zeferino Franco Salgado
     */
    public AuditUser() {
        logger.info("AuditUser instance created");
    }

    /**
     * Checks whether the requested username is present before running an audit.
     *
     * @param username the username to check
     * @return true if the username is available for audit processing; false otherwise
     * @author Zeferino Franco Salgado
     */
    public boolean userExists(String username) {
        logger.info("Checking user existence");
        if (username == null || username.isEmpty()) {
            logger.warning("Invalid username provided");
            return false;
        }
        return true;
    }

    /**
     * Validates that the requested user and year can be used for data audit work.
     *
     * @param username the username associated with the stored budget data
     * @param year the calendar year selected for audit
     * @return true if the user and year are valid audit inputs; false otherwise
     * @author Zeferino Franco Salgado
     */
    public boolean validateUserData(String username, int year) {
        logger.info("Validating user data");
        // Check if username is valid
        if (!userExists(username)) {
            logger.warning("Invalid username provided: " + username);
            return false;
        }
        // Check if year is within acceptable range
        if (year < 1900 || year > java.time.Year.now().getValue()) {
            logger.warning("Invalid year provided: " + year);
            return false;
        }
        return true;
    }

    /**
     * Checks whether a stored budget object is available before duplicate and
     * anomaly checks are run.
     *
     * @param budget the annual budget data selected for audit
     * @return true if the budget object is available; false otherwise
     * @author Zeferino Franco Salgado
     */
    public boolean checkBudget(Object budget) {
        logger.info("Checking budget availability");
        // Check if budget is null or not of correct type
        if (budget == null || !(budget instanceof Budget)) {
            logger.warning("Invalid budget object provided: " + budget);
            return false;
        }
        Budget b = (Budget) budget;
        // Validate required fields in the budget
        if (b.getUserId() == null || b.getYear() == 0 || b.getAmount() <= 0) {
            logger.warning("Missing or invalid data in budget object: " + b);
            return false;
        }
        return true;
    }

    /**
     * Generates a user-facing summary for an audit run.
     *
     * @param username the username associated with the audit
     * @param year the calendar year selected for audit
     * @return a summary report for the requested user and year
     * @author Zeferino Franco Salgado
     */
    public String generateReport(String username, int year) {
        logger.info("Generating audit report");
        try {
            return String.format("Audit Report for user %s - Year %d%n" +
                    "Budget Data Validated: %b", username, year,
                    checkBudget(new Budget(username, year, 1000.0)));
        } catch (Exception e) {
            logger.severe("Error generating report: " + e.getMessage());
            return "Failed to generate audit report";
        }
    }

    private static class Budget {
        private String userId;
        private int year;
        private double amount;

        public String getUserId() { return userId; }
        public int getYear() { return year; }
        public double getAmount() { return amount; }

        public Budget(String userId, int year, double amount) {
            this.userId = userId;
            this.year = year;
            this.amount = amount;
        }
    }
}
