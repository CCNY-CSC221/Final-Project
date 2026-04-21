package Validation;

/**
 * This class handles the main checks for the entire budget file.
 * * @author Sharif Parvez, Redwan Bari, Elham Mahmud, Jamis Blade (Validation Team)
 */
public class FileChecker {

    /**
     * Default constructor for FileChecker.
     * * @author Sharif Parvez
     */
    public FileChecker() {
    }

    /**
     * Runs all the validation steps for the file.
     * * @param filePath The path of the file to be checked
     * @return true if the file passes all checks, false otherwise
     * @author Sharif 
     */
    public boolean startFileCheck(String filePath) {
        return false; 
    }

    /**
     * Makes sure an individual line of data is correct.
     * * @param rowData A string representing a single row of data
     * @return true if the row is formatted correctly, false otherwise
     * @author Sharif 
     */
    public boolean checkRow(String rowData) {
        return false; 
    }

    /**
     * Ensures the column names are in the right places.
     * * @param headerRow The first line of the file containing headers
     * @return true if the headers match expected values, false otherwise
     * @author Sharif 
     */
    public boolean checkTopRow(String headerRow) {
        return false; 
    }

    /**
     * Verifies the file is named correctly.
     * * @param fileName The name of the uploaded file
     * @return true if the name follows the correct format, false otherwise
     * @author Sharif 
     */
    public boolean checkFileName(String fileName) {
        return false; 
    }

    /**
     * Confirms the data inside the file matches the year in the name.
     * * @param fileNameYear The year extracted from the file name
     * @param fileDataYear The year found inside the data rows
     * @return true if the years match, false otherwise
     * @author Sharif 
     */
    public boolean checkYear(int fileNameYear, int fileDataYear) {
        return false; 
    }

    /**
     * Checks storage to see if this year's data is already there.
     * * @param year The year to check against existing records
     * @return true if the year is new and safe to add, false if it already exists
     * @author Sharif 
     */
    public boolean checkNewYear(int year) {
        return false; 
    }
}