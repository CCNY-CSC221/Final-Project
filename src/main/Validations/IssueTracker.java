package Validations;

/**
 * This class keeps a list of every error found and shows it to the user.
 * * @author Sharif Parvez, Redwan Bari, Elham Mahmud, Jamis Bade (Validation Team)
 */
public class IssueTracker {

    /**
     * Default constructor for IssueTracker.
     * * @author Jamis
     */
    public IssueTracker() {
    }

    /**
     * Notes down a major problem that stops the upload.
     * * @param errorMessage The description of the error
     * @author Jamis
     */
    public void saveError(String errorMessage) {
    }

    /**
     * Notes down a small issue the user should know about.
     * * @param warningMessage The description of the warning
     * @author Jamis
     */
    public void saveWarning(String warningMessage) {
    }

    /**
     * Returns true if any big errors were found.
     * * @return true if the validation process failed due to errors, false otherwise
     * @author Jamis
     */
    public boolean checkFail() {
        return false; 
    }

    /**
     * Shows the user a list of all errors and warnings.
     * * @author Jamis
     */
    public void printResults() {
    }

    /**
     * Gives the final count of how many errors were found.
     * * @return an integer representing the total number of errors
     * @author Jamis
     */
    public int countErrors() {
        return 0; 
    }
}
