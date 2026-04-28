import java.util.ArrayList;
/**
 * This class keeps a list of every error found and shows it to the user.
 * * @author Sharif, Redwan, Elham, Jamis (Validation Team)
 */
public class IssueTracker {

    // Simple lists to hold our error and warning messages
    private ArrayList<String> errorList;
    private ArrayList<String> warningList;

    /**
     * Default constructor for IssueTracker.
     * * @author Jamis
     */
    public IssueTracker() {
        errorList = new ArrayList<String>();
        warningList = new ArrayList<String>();
    }

    /**
     * Notes down a major problem (like a fake date) that stops the upload.
     * * @param errorMessage The description of the error
     * @author Jamis
     */
    public void saveError(String errorMessage) {
        // Prevent duplicate error messages in the list
        if (!errorList.contains(errorMessage)) {
            errorList.add(errorMessage);
        }
    }

    /**
     * Notes down a smaller issue (like a duplicate row) for the user to see.
     * * @param warningMessage The description of the warning
     * @author Jamis
     */
    public void saveWarning(String warningMessage) {
        warningList.add(warningMessage);
    }

    /**
     * Returns true if any big errors were found.
     * * @return true if the validation failed, false otherwise
     * @author Jamis
     */
    public boolean checkFail() {
        // If the error list is not empty, we failed the check
        return errorList.size() > 0;
    }

    /**
     * Prints all saved errors and warnings to the console.
     * * @author Jamis
     */
    public void printResults() {
        // If both lists are empty, everything is perfect
        if (errorList.size() == 0 && warningList.size() == 0) {
            System.out.println("Validation complete. No issues found!");
            return;
        }

        // Print the major errors first
        if (errorList.size() > 0) {
            System.out.println("--- CRITICAL ERRORS ---");
            for (int i = 0; i < errorList.size(); i++) {
                System.out.println("Error: " + errorList.get(i));
            }
        }

        // Print the warnings next
        if (warningList.size() > 0) {
            System.out.println("\n--- WARNINGS ---");
            for (int i = 0; i < warningList.size(); i++) {
                System.out.println("Warning: " + warningList.get(i));
            }
        }
    }

    /**
     * Gives the final count of how many errors were found.
     * * @return total number of errors
     * @author Jamis
     */
    public int countErrors() {
        return errorList.size();
    }
}
