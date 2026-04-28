import java.util.ArrayList;
/**
 * This class looks for messy data or blank spots that might break the program.
 * * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class ErrorFinder {

    // A simple list to remember the rows we have already checked to find duplicates
    private ArrayList<String> seenRows;

    /**
     * Default constructor for ErrorFinder.
     * * @author Elham
     */
    public ErrorFinder() {
        // Start with an empty list of rows
        seenRows = new ArrayList<String>();
    }

    /**
     * Checks if the file has any data at all, or just the header.
     * * @param fileContent The entire content of the file
     * @return true if the file is empty, false otherwise
     * @author Elham
     */
    public boolean isEmpty(String fileContent) {
        // If the file is completely blank
        if (fileContent == null || fileContent.trim().isEmpty()) {
            return true; 
        }
        
        // If the file ONLY has the top row and no actual numbers
        if (fileContent.trim().equalsIgnoreCase("Date,Category,Amount")) {
            return true;
        }
        
        return false; 
    }

    /**
     * Finds rows where data was left blank (like a missing category or amount).
     * * @param rowData A single row of data
     * @return true if there is missing information, false if complete
     * @author Elham
     */
    public boolean isMissingInfo(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return true; 
        }

        // If the row starts or ends with a comma, a piece of data is missing
        if (rowData.startsWith(",") || rowData.endsWith(",")) {
            return true;
        }

        // Split the row by commas
        String[] pieces = rowData.split(",");
        
        // If we don't have exactly 3 pieces (Date, Category, Amount), it is incomplete
        if (pieces.length != 3) {
            return true;
        }

        // Check each piece to make sure it isn't just empty spaces
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].trim().isEmpty()) {
                return true; 
            }
        }

        return false; 
    }

    /**
     * Looks for rows that are repeated (identical date, category, and amount).
     * * @param rowData The row to check against history
     * @return true if it is a duplicate, false otherwise
     * @author Elham
     */
    public boolean isDuplicate(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return false;
        }

        String cleanRow = rowData.trim();

        // If our list already contains this exact row, it is a duplicate
        if (seenRows.contains(cleanRow)) {
            return true; 
        }

        // Otherwise, add it to our list so we remember it for the next row
        seenRows.add(cleanRow);
        return false; 
    }

    /**
     * Searches for weird characters (like @, #, or $) that aren't allowed.
     * * @param data The data string to check
     * @return true if bad symbols are found, false if clean
     * @author Elham
     */
    public boolean hasBadSymbols(String data) {
        if (data == null) {
            return false;
        }

        // Check every single character in the string one by one
        for (int i = 0; i < data.length(); i++) {
            char symbol = data.charAt(i);
            
            // Letters and numbers are always allowed
            if (Character.isLetterOrDigit(symbol)) {
                continue;
            }
            
            // These specific punctuation marks are allowed for our formats
            if (symbol == ' ' || symbol == ',' || symbol == '/' || symbol == '-') {
                continue;
            }
            
            // If it is any other symbol, it is a "bad symbol"
            return true; 
        }
        
        return false; 
    }
}
