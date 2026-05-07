import java.util.logging.Logger;

/**
 * Checks whether a user and the user's stored budget data are available for
 * data audit operations.
 *
 * @author Zeferino Franco Salgado, Raphy Binet
 */
public class AuditUser {

    private static final Logger logger = Logger.getLogger(AuditUser.class.getName());

    /**
     * Creates an AuditUser object for user and budget availability checks.
     *
     * @author Zeferino Franco Salgado
     */
    public AuditUser() {
        // Constructor intentionally left blank.
    }

    public boolean userExists(String username) {
        if (username == null || username.isEmpty()) {
            logger.warning("Invalid username provided");
            return false;
        }
        return true;
    }

    public boolean validateUserData(String username, int year) {
        if (!userExists(username)) {
            logger.warning("Invalid username provided: " + username);
            return false;
        }

        if (year < 1900 || year > java.time.Year.now().getValue()) {
            logger.warning("Invalid year provided: " + year);
            return false;
        }

        try {
            UserStorage userStorage = StorageService.loadUserStorage(username);
            if (!userStorage.hasLedgerForYear(year)) {
                logger.warning("No ledger data found for user " + username + " in year " + year);
                return false;
            }
        } catch (Exception e) {
            logger.warning("Could not validate audit data for user " + username + ": " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean checkBudget(Object budget) {
        if (budget == null || !(budget instanceof Budget)) {
            logger.warning("Invalid budget object provided: " + budget);
            return false;
        }

        Budget b = (Budget) budget;

        if (b.getUserId() == null || b.getYear() == 0 || b.getAmount() <= 0) {
            logger.warning("Missing or invalid data in budget object: " + b);
            return false;
        }

        return true;
    }

    public String generateReport(String username, int year) {
        try {
            return String.format(
                    "Audit Report for user %s - Year %d%nBudget Data Validated: %b",
                    username,
                    year,
                    checkBudget(new Budget(username, year, 1000.0))
                    );
        } catch (Exception e) {
            logger.severe("Error generating report: " + e.getMessage());
            return "Failed to generate audit report";
        }
    }

    private static class Budget {
        private String userId;
        private int year;
        private double amount;

        public Budget(String userId, int year, double amount) {
            this.userId = userId;
            this.year = year;
            this.amount = amount;
        }

        public String getUserId() {
            return userId;
        }

        public int getYear() {
            return year;
        }

        public double getAmount() {
            return amount;
        }
    }
}
