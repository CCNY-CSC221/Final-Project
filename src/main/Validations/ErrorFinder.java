package Validation;

/**
 * This class looks for messy data or blank spots that might break the program.
 * * @author Sharif Parvez, Redwan Bari, Elham Mahmud, Jamis Blade (Validation Team)
 */
public class ErrorFinder {

    /**
     * Default constructor for ErrorFinder.
     * * @author Elham 
     */
    public ErrorFinder() {
    }

    /**
     * Checks if the file has any data at all.
     * * @param fileContent The entire content of the file
     * @return true if the file is empty, false otherwise
     * @author Elham 
     */
    public boolean isEmpty(String fileContent) {
        return false; 
    }

    /**
     * Finds rows where data was left blank.
     * * @param rowData A single row of data
     * @return true if there is missing information, false if complete
     * @author Elham 
     */
    public boolean isMissingInfo(String rowData) {
        return false; 
    }

    /**
     * Looks for rows that are repeated by mistake.
     * * @param rowData The row to check against existing parsed rows
     * @return true if it is a duplicate, false otherwise
     * @author Elham 
     */
    public boolean isDuplicate(String rowData) {
        return false; 
    }

    /**
     * Confirms the file is actually a CSV file.
     * * @param fileName The name of the file
     * @return true if it ends with .csv, false otherwise
     * @author Elham 
     */
    public boolean isCSVFormat(String fileName) {
        return false; 
    }

    /**
     * Searches for weird characters that aren't allowed.
     * * @param data The data string to check
     * @return true if bad symbols are found, false if clean
     * @author Elham 
     */
    public boolean hasBadSymbols(String data) {
        return false; 
    }
}