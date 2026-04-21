package Validations;

/**
 * This class looks at the specific pieces of information in each row to catch typos or errors.
 * * @author Sharif Parvez, Redwan Bari, Elham Mahmud, Jamis Blade (Validation Team)
 */
public class DataValidator {

    /**
     * Default constructor for DataValidator.
     * * @author Redwan 
     */
    public DataValidator() {
    }

    /**
     * Checks if the date is a real day and formatted right.
     * * @param dateString The date input from the file
     * @return true if the date is valid, false otherwise
     * @author Redwan 
     */
    public boolean isValidDate(String dateString) {
        return false; 
    }

    /**
     * Makes sure the category is one we allow.
     * * @param category The category string to check
     * @return true if the category is on the approved list, false otherwise
     * @author Redwan 
     */
    public boolean isValidCategory(String category) {
        return false; 
    }

    /**
     * Confirms the money value is a number and not negative.
     * * @param amount The transaction amount as a string
     * @return true if the amount is a valid positive number, false otherwise
     * @author Redwan 
     */
    public boolean isValidAmount(String amount) {
        return false; 
    }

    /**
     * Checks if the input is a word, number, or date as expected.
     * * @param input The raw data input
     * @param expectedType The data type we expect (e.g., "String", "Double")
     * @return true if the input matches the expected type, false otherwise
     * @author Redwan 
     */
    public boolean isRightType(String input, String expectedType) {
        return false; 
    }

    /**
     * Makes sure the input matches our list of accepted words.
     * * @param input The value to check
     * @return true if the value is approved, false otherwise
     * @author Redwan 
     */
    public boolean isApprovedValue(String input) {
        return false; 
    }
}