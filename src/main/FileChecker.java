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
            String fileContent = FileFolderManager.readFile(filePath);

            if (fileContent == null || fileContent.trim().isEmpty()) {
                return false;
            }

            String[] rows = fileContent.split("\n");
            
            // Create the IssueTracker to log exact failures for the Test Team
            IssueTracker tracker = new IssueTracker();

            // Check header row first using the newly renamed method
            if (!isValidCSVHeader(rows[0])) {
                tracker.saveError("Validation failed: Invalid header format in row 1. Expected 'Date,Category,Amount'.");
                tracker.printResults();
                return false;
            }

            // Check interior data rows
            for (int i = 1; i < rows.length; i++) {
                if (rows[i].trim().isEmpty()) continue; // Skip empty lines safely
                
                if (!checkRow(rows[i])) {
                    // Log the exact line that broke
                    tracker.saveError("Validation failed on Row " + (i + 1) + ": " + rows[i]);
                    tracker.printResults(); // Print it to the console for the Test Team
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error processing the validation file.");
            return false;
        }
    }

    /**
     * Makes sure an individual line of data is correct.
     * Made private to hide internal logic from other modules.
     * @param rowData A string representing a single row of data
     * @return true if the row is formatted correctly, false otherwise
     * @author Sharif
     */
    private boolean checkRow(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return false;
        }

        String[] rowPieces = rowData.split(",", -1);

        // Project Spec: 3 parts
        if (rowPieces.length != 3) {
            return false;
        }

        String datePiece = rowPieces[0].trim();
        String categoryPiece = rowPieces[1].trim();
        String amountPiece = rowPieces[2].trim();

        return validator.isValidDate(datePiece) && 
               validator.isValidCategory(categoryPiece) && 
               validator.isValidAmount(amountPiece);
    }

    /**
     * Ensures the column names are in the right places.
     * Renamed and made public for the Integration Team.
     * @param headerRow The first line of the file
     * @return true if headers match expected values
     * @author Sharif
     */
    public boolean isValidCSVHeader(String headerRow) {
        if (headerRow == null || headerRow.trim().isEmpty()) {
            return false;
        }
        // Remove hidden BOM, use trim() to remove hidden return characters, and strip spaces
        String cleanHeader = headerRow.replace("\uFEFF", "").trim().replaceAll(" ", "");
        return cleanHeader.equalsIgnoreCase("Date,Category,Amount");
    }

    /**
     * Verifies the file is named correctly (Example: 2024.csv).
     * @param fileName The name of the file (Just the name, not the full path)
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

    /**
     * Extracts just the file name from a long computer file path.
     * Helpful for Integration Team when they have full C:/ paths.
     * @param fullPath The long file path string
     * @return Just the file name with the extension
     * @author Sharif
     */
    public String getFileNameFromPath(String fullPath) {
        if (fullPath == null) {
            return null;
        }
        
        // Change any backward Windows slashes to normal slashes to make finding it easy
        String simplePath = fullPath.replace("\\", "/");
        
        // Find the location of the very last slash in the text
        int lastSlashLocation = simplePath.lastIndexOf("/");
        
        // If a slash was found, cut off everything before it
        if (lastSlashLocation != -1) {
            return simplePath.substring(lastSlashLocation + 1);
        }
        
        // If there were no slashes, it is already just a file name
        return fullPath;
    }
}
