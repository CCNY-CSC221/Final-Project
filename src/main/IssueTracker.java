import java.util.ArrayList;

/**
 * This class tracks errors and warnings found during the file validation process.
 * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class IssueTracker {

    private ArrayList<String> errors;
    private ArrayList<String> warnings;

    /**
     * Default constructor for IssueTracker.
     * @author Jamis
     */
    public IssueTracker() {
        errors = new ArrayList<String>();
        warnings = new ArrayList<String>();
    }

    /**
     * Saves an error message if it is not already in the list.
     * @param error The error message to save
     * @author Jamis
     */
    public void saveError(String error) {
        if (error != null && !error.trim().isEmpty()) {
            if (!errors.contains(error)) {
                errors.add(error);
            }
        }
    }

    /**
     * Saves a warning message if it is not already in the list.
     * @param warning The warning message to save
     * @author Jamis
     */
    public void saveWarning(String warning) {
        if (warning != null && !warning.trim().isEmpty()) {
            // FIX: Added duplicate check to match saveError logic
            if (!warnings.contains(warning)) {
                warnings.add(warning);
            }
        }
    }

    /**
     * Checks if any hard errors were found during validation.
     * FIX: Renamed from checkFail() for better readability.
     * @return true if there are errors, false otherwise
     * @author Jamis
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * FIX: Clears all tracked errors and warnings. 
     * Essential for processing multiple files without carrying over old data.
     * @author Jamis
     */
    public void clear() {
        errors.clear();
        warnings.clear();
    }

    /**
     * Prints all tracked errors and warnings to the console.
     * FIX: Converted to for-each loops for safer iteration.
     * @author Jamis
     */
    public void printResults() {
        if (errors.isEmpty() && warnings.isEmpty()) {
            System.out.println("Validation passed with no issues.");
            return;
        }

        if (!errors.isEmpty()) {
            System.out.println("Errors found:");
            for (String error : errors) {
                System.out.println(" - " + error);
            }
        }

        if (!warnings.isEmpty()) {
            System.out.println("Warnings found:");
            for (String warning : warnings) {
                System.out.println(" - " + warning);
            }
        }
    }
}
