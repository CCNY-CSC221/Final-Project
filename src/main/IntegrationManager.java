/**
 * Starts the program, controls the menu flow, and calls other modules
 * depending on the user's menu choice.
 *
 * @author Dmytro Shumlianskyi
*/
public class IntegrationManager {
    
    private final FrontendConnector frontendConnector;

    /**
     * Creates an IntegrationManager object.
     *
     * @author Dmytro Shumlianskyi
     */
    public IntegrationManager() {
        frontendConnector = new FrontendConnector();
    }

    /**
     * Prepares the system before the application menu starts.
     *
     * @return true if the system is ready; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean initializeSystem() {
        // TODO:
        // Later this can check if required modules are available.
        // return true so the menu flow can be tested.
        return true;
    }

    /**
     * Starts the application.
     *
     * @author Dmytro Shumlianskyi
     */
    public void startApplication() {
        if(!initializeSystem()) {
            System.err.println("System startup failed.");
            return;
        }

        boolean running = true;
        boolean authenticated = false;

        while(running && !authenticated) {
            int option = frontendConnector.showAuthenticationMenu();
            
            switch (option) {
                case 1:
                    // TODO:
                    // Later this should call Accounts.signIn().
                    // If login is successful, set authenticated = true.
                    authenticated = true;
                    System.out.println("Login successful.");
                    break;

                case 2:
                    // TODO:
                    // Later this should call Accounts.createAccount().
                    System.out.println("Create account selected.");
                    break;
                    
                case 3:
                    // TODO:
                    // Later this should call Accounts.resetPasswordBySecretQuestion().
                    System.out.println("Reset password selected.");
                    break;

                case 4:
                    running = false;
                    System.out.println("Program closed.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }

        while(running && authenticated) {
            int option = frontendConnector.showMainMenu();
            
            switch (option) {
                case 1:
                    // TODO:
                    // Later this should call Validation first.
                    // If validation succeeds, call Storage to save the CSV data.
                    System.out.println("Load CSV selected.");
                    break;

                case 2:
                    // TODO:
                    // Later this should call the Reports module.
                    System.out.println("Generate report selected.");
                    break;

                case 3:
                    // TODO:
                    // Later this should call the Insights module.
                    System.out.println("Run insights selected.");
                    break;

                case 4:
                    // TODO:
                    // Later this should call the Data Audit module.
                    System.out.println("Run data audit selected.");
                    break;

                case 5:
                    // TODO:
                    // Later this should call Storage to delete selected year data.
                    System.out.println("Delete year data selected.");
                    break;

                case 6: 
                    handleAccountSettingsMenu();
                    break;

                case 7: 
                    running = false;
                    System.out.println("Program closed.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    /**
     * Handles the account settings menu flow.
     *
     * @author Dmytro Shumlianskyi
     */
    private void handleAccountSettingsMenu() {
        boolean inAccountSettings = true;

        while (inAccountSettings) {
            int option = frontendConnector.showAccountSettingsMenu();

            switch (option) {
                case 1:
                    // TODO:
                    // Later this should call Accounts.changePassword().
                    System.out.println("Change password selected.");
                    break;

                case 2:
                    // TODO:
                    // Later this should call Accounts.updateAccount().
                    System.out.println("Update secret question selected.");
                    break;

                case 3:
                    // TODO:
                    // Later this should call Accounts.deleteAccount().
                    System.out.println("Delete account selected.");
                    break;

                case 4:
                    inAccountSettings = false;
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    /**
     * Handles system exceptions and returns a readable error message.
     *
     * @param exception the exception that happened
     * @return a message about the handled error
     * @author Dmytro Shumlianskyi
     */
    public String handleException(Exception exception) {
        String message = exception.getMessage();

        if (message == null || message.isEmpty()) {
            message = "Unknown error occurred.";
        }

        return "Error handled: " + message;
    }
}