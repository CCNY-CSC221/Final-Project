/**
 * Starts the program, controls the menu flow, and calls other modules
 * depending on the user's menu choice.
 *
 * @author Dmytro Shumlianskyi
*/
public class IntegrationManager {
    
    private final FrontendConnector frontendConnector;
    private final FileChecker fileChecker;

    /**
     * Creates an IntegrationManager object.
     *
     * @author Dmytro Shumlianskyi
     */
    public IntegrationManager() {
        frontendConnector = new FrontendConnector();
        fileChecker = new FileChecker();
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
        if (!initializeSystem()) {
            System.err.println("System startup failed.");
            return;
        }

        boolean running = true;
        boolean authenticated = false;

        while (running && !authenticated) {
            int option = frontendConnector.showAuthenticationMenu();
            
            switch (option) {
                case 1:
                    // TODO:
                    // Later this should get username and password from the user
                    // and call Accounts.signIn(username, password).
                    authenticated = true;
                    System.out.println("Login successful.");
                    break;

                case 2:
                    // TODO:
                    // Later this should get account information from the user
                    // and call Accounts.createAccount().
                    System.out.println("Create account selected.");
                    break;
                    
                case 3:
                    // Opens forgot password menu.
                    handleForgotPasswordMenu();
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
                    // Load Year CSV data
                    handleLoadYearlyCSV();
                    break;

                case 2:
                    // Opens year data menu
                    handleYearDataMenu();
                    break;

                case 3:
                    // Opens report menu
                    handleReportMenu();
                    break;

                case 4:
                    // Opens insights menu
                    handleInsightsMenu();
                    break;

                case 5:
                    // Opens data audit menu
                    handleDataAuditMenu();
                    break;

                case 6: 
                    // Opens the account settings menu
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
     * Handles the forgot password menu flow.
     *
     * @author Dmytro Shumlianskyi
     */
    private void handleForgotPasswordMenu() {
        boolean inForgotPasswordMenu = true;

        while (inForgotPasswordMenu) {
            int option = frontendConnector.showForgotPasswordMenu();

            switch (option) {
                case 1:
                    // TODO: Get username, secret answer, and new password.
                    // Later this should call Accounts.resetPasswordBySecretQuestion().
                    System.out.println("Reset password by secret question selected.");
                    break;

                case 2:
                    inForgotPasswordMenu = false;
                    System.out.println("Returning to authentication menu.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    /**
     * Handles the Load Yearly CSV menu option.
     *
     * @author Khattab Sulaiman
     */
    private void handleLoadYearlyCSV() {
        try {
            System.out.print("Enter yearly CSV file path: ");
            String filePath = frontendConnector.readTextInput();

            boolean filePassed = fileChecker.startFileCheck(filePath);

            if (filePassed) {
                System.out.println("CSV file passed validation and is ready to load.");
            } else {
                System.out.println("CSV file failed validation. Please check the file and try again.");
            }

        } catch (Exception exception) {
            System.out.println(handleException(exception));
        }
    }

    /**
     * Handles the Manage Year Data menu flow.
     *
     * @author Dmytro Shumlianskyi
     */
    private void handleYearDataMenu() {
        boolean inYearDataMenu = true;

        while (inYearDataMenu) {
            int option = frontendConnector.showYearDataMenu();

            switch (option) {
                case 1:
                    // TODO:
                    // Later this should call Storage to show all saved years
                    // for the current logged in user.
                    System.out.println("View saved years selected.");
                    break;

                case 2:
                    // TODO:
                    // Later this should ask for the year
                    // and call Storage to delete that year data.
                    System.out.println("Delete year data selected.");
                    break;

                case 3:
                    inYearDataMenu = false;
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    /**
     * Handles the Reports menu flow.
     *
     * @author Dmytro Shumlianskyi
     */
    private void handleReportMenu() {
        boolean inReportMenu = true;

        while (inReportMenu) {
            int option = frontendConnector.showReportMenu();

            switch (option) {
                case 1:
                    // TODO:
                    // Later this should ask for the year
                    // and call Reports.generateAnnualReport().
                    System.out.println("Annual report selected.");
                    break;

                case 2:
                    // TODO:
                    // Later this should ask for the year
                    // and call Reports.generateCategoryReport().
                    System.out.println("Category report selected.");
                    break;

                case 3:
                    // TODO:
                    // Later this should ask for the year
                    // and call Reports.generateMonthlySummary().
                    System.out.println("Monthly summary selected.");
                    break;

                case 4:
                    inReportMenu = false;
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    /**
     * Handles the Insights menu flow.
     *
     * @author Dmytro Shumlianskyi
     */
    private void handleInsightsMenu() {
        boolean inInsightsMenu = true;

        while (inInsightsMenu) {
            int option = frontendConnector.showInsightsMenu();

            switch (option) {
                case 1:
                    // TODO:
                    // Later this should call Insights.calculatePercentageBreakdown().
                    System.out.println("Spending percentage breakdown selected.");
                    break;

                case 2:
                    // TODO:
                    // Later this should call Insights.analyzeDeficit().
                    System.out.println("Deficit suggestions selected.");
                    break;

                case 3:
                    // TODO:
                    // Later this should call InsightsOutput.exportToCSV().
                    System.out.println("Export insights to CSV selected.");
                    break;

                case 4:
                    inInsightsMenu = false;
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    /**
     * Handles the Data Audit menu flow.
     *
     * @author Dmytro Shumlianskyi
     */
    private void handleDataAuditMenu() {
        boolean inDataAuditMenu = true;

        while (inDataAuditMenu) {
            int option = frontendConnector.showDataAuditMenu();

            switch (option) {
                case 1:
                    // TODO:
                    // Later this should ask for the year
                    // and call the Data Audit module to run the audit.
                    System.out.println("Run audit for year selected.");
                    break;

                case 2:
                    // TODO:
                    // Later this should call StoreAudit.saveAudit().
                    System.out.println("Save audit report selected.");
                    break;

                case 3:
                    // TODO:
                    // Later this should call StoreAudit.purgeRecords().
                    System.out.println("Delete old audit records selected.");
                    break;

                case 4:
                    inDataAuditMenu = false;
                    System.out.println("Returning to main menu.");
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
