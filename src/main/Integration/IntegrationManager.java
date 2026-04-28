package Integration;

/**
 * Starts the system, connects modules, checks connections,
 * and handles errors.
 *
 * @author Dmytro Shumlianskyi
 */
public class IntegrationManager {
    
    private SystemMonitor systemMonitor;

    // PLACEHOLDER:
    // These booleans are temporary until real module objects are connected.
    private boolean accountsConnected;
    private boolean storageConnected;
    private boolean validationConnected;
    private boolean reportsConnected;
    private boolean insightsConnected;
    private boolean auditConnected;

    /**
     * Creates an IntegrationManager object.
     *
     * @author Dmytro Shumlianskyi
     */
    public IntegrationManager() {
        systemMonitor = new SystemMonitor();

        // PLACEHOLDER:
        // All modules start as not connected until connectModules() runs.
        accountsConnected = false;
        storageConnected = false;
        validationConnected = false;
        reportsConnected = false;
        insightsConnected = false;
        auditConnected = false;
    }

    /**
     * Starts the application.
     *
     * @return true if startup is successful; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean initializeSystem() {
        connectModules();

        return validateConnections();
    }

    /**
     * Connects all required modules.
     *
     * @return true if all modules connect; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean connectModules() {
        // PLACEHOLDER:
        // These values are set to true for now so IntegrationManager can be tested.
        // Later, each value should check if the real module object exists and works
        accountsConnected = true;
        storageConnected = true;
        validationConnected = true;
        reportsConnected = true;
        insightsConnected = true;
        auditConnected = true;

        return validateConnections();
    }

    /**
     * Checks whether module connections are working.
     *
     * @return true if all modules work; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean validateConnections() {
        String status = systemMonitor.checkSystemStatus(accountsConnected,
                storageConnected,
                validationConnected,
                reportsConnected,
                insightsConnected,
                auditConnected);

        return status.equals("System Status: Healthy (all modules connected)");
    }

    /**
     * Handles system exceptions.
     *
     * @param exception the exception that happened
     * @return a message about the handled error
     * @author Dmytro Shumlianskyi
     */
    public String handleExceptions(Exception exception) {
        String message = exception.getMessage();

        if (message == null || message.isEmpty()) {
            message = "Unknown error occurred.";
        }

        systemMonitor.reportErrors("Integration", message);

        return "Error handled: " + message;
    }
}