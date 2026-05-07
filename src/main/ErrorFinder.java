import java.util.ArrayList;

/**
 * This class looks for messy data or blank spots that might break the program.
 * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class ErrorFinder {

    // Store the header as a constant to improve maintainability 
    private static final String HEADER = "Date,Category,Amount";

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
        
        String cleanContent = fileContent.replaceAll(" ", "");
        String cleanHeader = HEADER.replaceAll(" ", "");
        
        if (cleanContent.equalsIgnoreCase(cleanHeader)) {
            return true;
        }
        
        return false; 
    }

    /**
     * Finds rows where data was left blank.
     * @param rowData A single row of data
     * @return true if there is missing information, false if complete
     * @author Elham
     */
    public boolean isMissingInfo(String rowData) {
        if (rowData == null || rowData.trim().isEmpty()) {
            return true; 
        }

        // Split by commas (the -1 ensures it counts trailing commas as empty fields)
        String[] pieces = rowData.split(",", -1);
        
        // Project Spec: exactly 3 pieces
        if (pieces.length != 3) {
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
            
            // Allowed symbols: space, comma, slash (for dates), dash (for negative amounts)
            // Explicitly blocking periods (.) because the rubric requires whole dollars with no cents.
            if (symbol == ' ' || symbol == ',' || symbol == '/' || symbol == '-') {
                continue;
            }
            return true; 
        }
        return false; 
    }
}
