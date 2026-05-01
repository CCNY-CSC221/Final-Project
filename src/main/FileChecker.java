import java.io.IOException;
// Import the Storage Team's tools
// import Storage.FileFolderManager; 
// import Storage.StorageService;

/**
 * This class handles the main checks for the entire budget file.
 * * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class FileChecker {

    private DataValidator validator;

    /**
     * Default constructor for FileChecker.I
     * * @author Sharif
     */
    public FileChecker() {
        this.validator = new DataValidator();
    }

    /**
     * Runs all the validation steps for the file.
     * * @param filePath The path of the file to be checked
     * @return true if the file passes all checks, false otherwise
     * @author Sharif
     */
    public boolean startFileCheck(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        try {
            // We removed the placeholder and are now using the real Storage method
            String fileContent = FileFolderManager.readFile(filePath);

            if (fileContent == null || fileContent.trim().isEmpty()) {
                return false;
            }

            // Split the file into rows
            String[] rows = fileContent.split("\n");

            // Check if the header row is correct
            if (!checkTopRow(rows[0])) {
                return false;
            }

            // Loop through the data rows (skip the header at index 0)
            for (int i = 1; i < rows.length; i++) {
                if (!checkRow(rows[i])) {
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Makes sure an individual line of data is correct.
     * * @param rowData A string representing a single row of data
     * @return true if the row is formatted correctly, false otherwise
     * @author Sharif
     */
    public boolean checkRow(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return false;
        }

        String[] rowPieces = rowData.split(",");

        if (rowPieces.length != 3) {
            return false;
        }

        // Pass each piece to our DataValidator class
        String datePiece = rowPieces[0].trim();
        String categoryPiece = rowPieces[1].trim();
        String amountPiece = rowPieces[2].trim();

        return validator.isValidDate(datePiece) && 
               validator.isValidCategory(categoryPiece) && 
               validator.isValidAmount(amountPiece);
    }

    /**
     * Ensures the column names are in the right places.
     * * @param headerRow The first line of the file
     * @return true if headers match expected values
     * @author Sharif
     */
    public boolean checkTopRow(String headerRow) {
        if (headerRow == null || headerRow.trim().isEmpty()) {
            return false;
        }
        return headerRow.trim().equalsIgnoreCase("Date,Category,Amount");
    }

    /**
     * Verifies the file is named correctly (Example: 2024.csv).
     * * @param fileName The name of the file
     * @return true if name is valid
     * @author Sharif
     */
    public boolean checkFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        String cleanName = fileName.trim();

        // Must be exactly 8 characters: 4 digits + .csv
        if (cleanName.length() != 8 || !cleanName.endsWith(".csv")) {
            return false;
        }

        // Check the first 4 characters for numbers
        for (int i = 0; i < 4; i++) {
            if (!Character.isDigit(cleanName.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Confirms the file year matches the interior data year.
     * * @param fileNameYear Year from name
     * @param fileDataYear Year from rows
     * @return true if match
     * @author Sharif
     */
    public boolean checkYear(int fileNameYear, int fileDataYear) {
        return fileNameYear == fileDataYear;
    }

    /**
     * Checks storage to see if this user already has this year's data.
     * UPDATED: Accepts username from the Accounts Team.
     * * @param username The name of the current user
     * @param year The year to check
     * @return true if new, false if it exists
     * @author Sharif
     */
    public boolean checkNewYear(String username, int year) {
        try {
            // Integration: This will be called by the Accounts Team 
            // once they integrate our check into their login/load flow.
            
            // UserStorage user = StorageService.loadUserStorage(username);
            // return !user.hasLedgerForYear(year);
            
            return true; // Default to true until Storage logic is fully ready
        } catch (Exception e) {
            return true;
        }
    }
}
