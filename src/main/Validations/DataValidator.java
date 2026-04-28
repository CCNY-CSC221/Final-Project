/**
 * This class looks at the specific pieces of information in each row to catch typos or errors.
 * * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class DataValidator {

    // A simple array holding the 13 approved categories from the edge_2024.csv test file
    private String[] approvedCategories = {
        "Compensation", "Food", "Home", "Utilities", "Transportation", 
        "Entertainment", "Appearance", "Work", "Education", 
        "Professional Services", "Allowance", "Investments", "Other"
    };

    /**
     * Default constructor for DataValidator.
     * * @author Redwan
     */
    public DataValidator() {
    }

    /**
     * Checks if the date is a real day and formatted right (MM/DD/YYYY).
     * Uses manual beginner logic instead of advanced Java time libraries.
     * * @param dateString The date input from the file
     * @return true if the date is valid, false otherwise
     * @author Redwan
     */
    public boolean isValidDate(String dateString) {
        // If the string is empty or null, it is bad data
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }

        // Split the date by the slash symbol
        String[] dateParts = dateString.split("/");

        // A correct date should have exactly 3 parts: Month, Day, Year
        if (dateParts.length != 3) {
            return false;
        }

        try {
            // Convert the string parts into actual numbers
            int month = Integer.parseInt(dateParts[0].trim());
            int day = Integer.parseInt(dateParts[1].trim());
            int year = Integer.parseInt(dateParts[2].trim());

            // Basic checks: Month must be 1-12, Day must be 1-31
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }

            // Check for months that only have 30 days (April, June, September, November)
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return false;
            }

            // Special check for February
            if (month == 2) {
                // Leap year math: divisible by 4, but not 100, unless divisible by 400
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                
                // If it is a leap year, February can't have more than 29 days
                if (isLeapYear && day > 29) {
                    return false;
                }
                // If it is NOT a leap year, February can't have more than 28 days
                if (!isLeapYear && day > 28) {
                    return false;
                }
            }

            // If it passes all checks, it is a real date
            return true;

        } catch (Exception e) {
            // If Integer.parseInt fails (e.g., they typed letters instead of numbers)
            return false;
        }
    }

    /**
     * Makes sure the category is one we allow.
     * * @param category The category string to check
     * @return true if the category is on the approved list, false otherwise
     * @author Redwan
     */
    public boolean isValidCategory(String category) {
        // Reject blank categories
        if (category == null || category.trim().isEmpty()) {
            return false;
        }

        String cleanCategory = category.trim();

        // Loop through our simple array one by one
        for (int i = 0; i < approvedCategories.length; i++) {
            // Check if the current word matches our list (ignoring upper/lowercase)
            if (approvedCategories[i].equalsIgnoreCase(cleanCategory)) {
                return true; 
            }
        }
        
        // If the loop finishes and finds no match, it is a bad category
        return false;
    }

    /**
     * Confirms the money value is a whole number (positive or negative).
     * * @param amount The transaction amount as a string
     * @return true if the amount is a valid integer, false otherwise
     * @author Redwan
     */
    public boolean isValidAmount(String amount) {
        // Reject blank amounts
        if (amount == null || amount.trim().isEmpty()) {
            return false;
        }
        
        try {
            // This attempts to turn the text into an integer.
            // It will automatically fail if there is a decimal (.) or letters.
            Integer.parseInt(amount.trim());
            return true;
        } catch (Exception e) {
            // Caught an error, meaning it was not a valid whole number
            return false;
        }
    }

    /**
     * Checks if the input is a word, number, or date as expected.
     * * @param input The raw data input
     * @param expectedType The data type we expect ("Date", "Integer", "String")
     * @return true if the input matches the expected type, false otherwise
     * @author Redwan
     */
    public boolean isRightType(String input, String expectedType) {
        if (input == null) {
            return false;
        }
        
        String cleanType = expectedType.trim().toLowerCase();
        
        // Simple if/else chain to route the check to the right method
        if (cleanType.equals("date")) {
            return isValidDate(input);
        } else if (cleanType.equals("integer")) {
            return isValidAmount(input);
        } else if (cleanType.equals("string")) {
            // Strings just can't be completely blank
            return !input.trim().isEmpty();
        }
        
        return false;
    }

    /**
     * Makes sure the input matches our list of accepted words.
     * * @param input The value to check
     * @return true if the value is approved, false otherwise
     * @author Redwan
     */
    public boolean isApprovedValue(String input) {
        // Right now, the only approved words we check are categories
        return isValidCategory(input);
    }
}
