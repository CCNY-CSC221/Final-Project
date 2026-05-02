/**
 * This class looks at the specific pieces of information in each row to catch typos or errors.
 * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class DataValidator {

    // A simple array holding the 13 approved categories
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
     * Checks if the date is a real day and formatted right (YYYY-MM-DD).
     * @param dateString The date input from the file
     * @return true if the date is valid, false otherwise
     * @author Redwan
     */
    public boolean isValidDate(String dateString) {
        // If the string is empty or null, it is bad data
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }

        // Split the date by the dash symbol (YYYY-MM-DD)
        String[] dateParts = dateString.split("-");

        // A correct date should have exactly 3 parts: Year, Month, Day
        if (dateParts.length != 3) {
            return false;
        }

        try {
            // Convert the string parts into actual numbers
            int year = Integer.parseInt(dateParts[0].trim());
            int month = Integer.parseInt(dateParts[1].trim());
            int day = Integer.parseInt(dateParts[2].trim());

            // Basic checks: Month must be 1-12, Day must be 1-31
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }

            // Check for months that only have 30 days
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return false;
            }

            // Special check for February
            if (month == 2) {
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                
                if (isLeapYear && day > 29) {
                    return false;
                }
                if (!isLeapYear && day > 28) {
                    return false;
                }
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
     * Confirms the money value is a positive number (allows decimals).
     * @param amount The transaction amount as a string
     * @return true if the amount is valid, false otherwise
     * @author Redwan
     */
    public boolean isValidAmount(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            return false;
        }
        try {
            // Use Double to allow decimals like 15.50
            double val = Double.parseDouble(amount.trim());
            // Storage Team requires amounts to be positive (or zero)
            return val >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the type is exactly 'income' or 'expense'.
     * @param type The type string to check
     * @return true if valid type, false otherwise
     * @author Redwan
     */
    public boolean isValidType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return false;
        }
        String cleanType = type.trim().toLowerCase();
        return cleanType.equals("income") || cleanType.equals("expense");
    }
}
