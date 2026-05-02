/**
 * This class handles the main checks for the entire budget file.
 * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class FileChecker {

    private DataValidator validator;

    /**
     * Default constructor for FileChecker.
     * @author Sharif
     */
    public FileChecker() {
        this.validator = new DataValidator();
    }

    /**
     * Runs all the validation steps for the file.
     * @param filePath The path of the file to be checked
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

            String[] rows = fileContent.split("\n");

            if (!checkTopRow(rows[0])) {
                return false;
            }

            for (int i = 1; i < rows.length; i++) {
                if (rows[i].trim().isEmpty()) continue; // Skip empty lines safely
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
     * @param rowData A string representing a single row of data
     * @return true if the row is formatted correctly, false otherwise
     * @author Sharif
     */
    public boolean checkRow(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return false;
        }

        // Split by commas (the -1 catches missing columns at the end)
        String[] rowPieces = rowData.split(",", -1);

        // Expecting 5 parts: Date, Category, Description, Amount, Type
        if (rowPieces.length != 5) {
            return false;
        }

        String datePiece = rowPieces[0].trim();
        String categoryPiece = rowPieces[1].trim();
        String descPiece = rowPieces[2].trim();
        String amountPiece = rowPieces[3].trim();
        String typePiece = rowPieces[4].trim();

        return validator.isValidDate(datePiece) && 
               validator.isValidCategory(categoryPiece) && 
               !descPiece.isEmpty() &&
               validator.isValidAmount(amountPiece) &&
               validator.isValidType(typePiece);
    }

    /**
     * Ensures the column names are in the right places.
     * @param headerRow The first line of the file
     * @return true if headers match expected values
     * @author Sharif
     */
    public boolean checkTopRow(String headerRow) {
        if (headerRow == null || headerRow.trim().isEmpty()) {
            return false;
        }
        // Remove all spaces so we get an exact match no matter what
        String cleanHeader = headerRow.replaceAll(" ", "");
        return cleanHeader.equalsIgnoreCase("Date,Category,Description,Amount,Type");
    }

    /**
     * Verifies the file is named correctly (Example: 2024.csv).
     * @param fileName The name of the file
     * @return true if name is valid
     * @author Sharif
     */
    public boolean checkFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        String cleanName = fileName.trim();

        if (cleanName.length() != 8 || !cleanName.endsWith(".csv")) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (!Character.isDigit(cleanName.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Confirms the file year matches the interior data year.
     * @param fileNameYear Year from name
     * @param fileDataYear Year from rows
     * @return true if match
     * @author Sharif
     */
    public boolean checkYear(int fileNameYear, int fileDataYear) {
        return fileNameYear == fileDataYear;
    }

    /**
     * Checks storage to see if this user already has this year's data.
     * @param username The name of the current user
     * @param year The year to check
     * @return true if new, false if it exists
     * @author Sharif
     */
    public boolean checkNewYear(String username, int year) {
        try {
            return true; // Default to true until Storage logic is fully ready
        } catch (Exception e) {
            return true;
        }
    }
}
