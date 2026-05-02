import java.util.ArrayList;

/**
 * This class looks for messy data or blank spots that might break the program.
 * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class ErrorFinder {

    // A simple list to remember the rows we have already checked
    private ArrayList<String> seenRows;

    /**
     * Default constructor for ErrorFinder.
     * @author Elham
     */
    public ErrorFinder() {
        seenRows = new ArrayList<String>();
    }

    /**
     * Checks if the file has any data at all, or just the header.
     * @param fileContent The entire content of the file
     * @return true if the file is empty, false otherwise
     * @author Elham
     */
    public boolean isEmpty(String fileContent) {
        if (fileContent == null || fileContent.trim().isEmpty()) {
            return true; 
        }
        
        // Remove spaces to make the check foolproof
        String cleanContent = fileContent.replaceAll(" ", "");
        if (cleanContent.equalsIgnoreCase("Date,Category,Description,Amount,Type")) {
            return true;
        }
        
        return false; 
    }

    /**
     * Finds rows where data was left blank (like a missing category or amount).
     * @param rowData A single row of data
     * @return true if there is missing information, false if complete
     * @author Elham
     */
    public boolean isMissingInfo(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return true; 
        }

        if (rowData.startsWith(",") || rowData.endsWith(",")) {
            return true;
        }

        // Split the row by commas (the -1 ensures it counts trailing commas)
        String[] pieces = rowData.split(",", -1);
        
        // Now expecting 5 pieces
        if (pieces.length != 5) {
            return true;
        }

        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].trim().isEmpty()) {
                return true; 
            }
        }

        return false; 
    }

    /**
     * Looks for rows that are repeated.
     * @param rowData The row to check against history
     * @return true if it is a duplicate, false otherwise
     * @author Elham
     */
    public boolean isDuplicate(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return false;
        }
        String cleanRow = rowData.trim();
        if (seenRows.contains(cleanRow)) {
            return true; 
        }
        seenRows.add(cleanRow);
        return false; 
    }

    /**
     * Searches for weird characters that aren't allowed.
     * @param data The data string to check
     * @return true if bad symbols are found, false if clean
     * @author Elham
     */
    public boolean hasBadSymbols(String data) {
        if (data == null) {
            return false;
        }

        for (int i = 0; i < data.length(); i++) {
            char symbol = data.charAt(i);
            
            if (Character.isLetterOrDigit(symbol)) {
                continue;
            }
            
            // Added period (.) for decimals, and dash (-) for dates
            if (symbol == ' ' || symbol == ',' || symbol == '-' || symbol == '.') {
                continue;
            }
            return true; 
        }
        return false; 
    }
}
