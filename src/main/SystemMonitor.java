import java.util.ArrayList;

/**
 * Checks system health and reports errors.
 *
 * @author Dmytro Shumlianskyi
 */
public class SystemMonitor {

    private ArrayList<String> errors;

    /**
     * Creates a SystemMonitor object.
     *
     * @author Dmytro Shumlianskyi
     */
    public SystemMonitor() {
        errors = new ArrayList<>();
    }

    /**
     * Checks system status.
     *
     * @param accountsConnected true if Accounts module is connected
     * @param storageConnected true if Storage module is connected
     * @param validationConnected true if Validation module is connected
     * @param reportsConnected true if Reports module is connected
     * @param insightsConnected true if Insights module is connected
     * @param auditConnected true if Data Audit module is connected
     * @return the current system status
     * @author Dmytro Shumlianskyi
    */
    public String checkSystemStatus(boolean accountsConnected, 
                                    boolean storageConnected, 
                                    boolean validationConnected, 
                                    boolean reportsConnected,
                                    boolean insightsConnected, 
                                    boolean auditConnected) {

        boolean allModulesConnected = accountsConnected
            && storageConnected
            && validationConnected
            && reportsConnected
            && insightsConnected
            && auditConnected;

        if (allModulesConnected) {
            return "System Status: Healthy (all modules connected)";
        }
        
        return "System Status: Degraded (one or more modules unavailable)"; 
    }

    /**
     * Logs system errors.
     *
     * @param moduleName the module with the error
     * @param description the error description
     * @return a log message
     * @author Dmytro Shumlianskyi
     */
    public String reportErrors(String moduleName, String description) {
        String errorMessage = moduleName + ": " + description;
        errors.add(errorMessage);

        return errorMessage;
    }

    /**
     * Notifies about module failures.
     *
     * @param moduleName the module name
     * @param hasError true if there is an error; false otherwise
     * @return a notification message
     * @author Dmytro Shumlianskyi
     */
    public String notifyTeams(String moduleName, boolean hasError) {
        if (!hasError) {
            return "No errors detected.";
        }

        String message = "Failure detected in " + moduleName + " module.";

        if (!errors.isEmpty()) {
            message = message + "\nLatest error: " + errors.get(errors.size() - 1);
        }

        return message;
    }
}