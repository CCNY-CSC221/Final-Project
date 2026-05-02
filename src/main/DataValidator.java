/**
 * This class looks at the specific pieces of information in each row to catch typos or errors.
 * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class DataValidator {

    private String[] approvedCategories = {
        "Compensation", "Food", "Home", "Utilities", "Transportation", 
        "Entertainment", "Appearance", "Work", "Education", 
        "Professional Services", "Allowance", "Investments", "Other"
    };

    /**
     * Default constructor for DataValidator.
     * @author Redwan
     */
    public DataValidator() {
    }

    /**
     * Checks if the date is a real day and formatted right (MM/DD/YYYY).
     * @param dateString The date input from the file
     * @return true if the date is valid, false otherwise
     * @author Redwan
     */
    public boolean isValidDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }

        // Project Spec: MM/DD/YYYY
        String[] dateParts = dateString.split("/");

        if (dateParts.length != 3) {
            return false;
        }

        try {
            // Index 0 is Month, Index 1 is Day, Index 2 is Year
            int month = Integer.parseInt(dateParts[0].trim());
            int day = Integer.parseInt(dateParts[1].trim());
            int year = Integer.parseInt(dateParts[2].trim());

            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;

            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return false;
            }

            if (month == 2) {
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                if (isLeapYear && day > 29) return false;
                if (!isLeapYear && day > 28) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Makes sure the category is one we allow.
     * @param category The category string to check
     * @return true if the category is on the approved list, false otherwise
     * @author Redwan
     */
    public boolean isValidCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }
        String cleanCategory = category.trim();

        for (int i = 0; i < approvedCategories.length; i++) {
            if (approvedCategories[i].equalsIgnoreCase(cleanCategory)) {
                return true; 
            }
        }
        return false;
    }

    /**
     * Confirms the money value is a whole integer (positive or negative, no decimals).
     * @param amount The transaction amount as a string
     * @return true if the amount is valid, false otherwise
     * @author Redwan
     */
    public boolean isValidAmount(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            return false;
        }
        try {
            // Project Spec: Whole dollar value (no cents). Income is positive, expenses negative.
            Integer.parseInt(amount.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
