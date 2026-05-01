/**
 * Starts the program, controls the menu flow, and calls other modules
 * depending on the user's menu choice.
 *
 * @author Dmytro Shumlianskyi
*/
public class IntegrationManager {
    
    private final FrontendConnector frontendConnector;
    private final FileChecker fileChecker;
    private final Accounts accountManager;
    private final AccountStorage accountStorage;

    private String currentUsername;

    /**
     * Creates an IntegrationManager object.
     *
     * @author Dmytro Shumlianskyi
     */
    public IntegrationManager() {
        frontendConnector = new FrontendConnector();
        fileChecker = new FileChecker();
        accountManager = new Accounts();
        accountStorage = new AccountStorage();
        currentUsername = "";
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
            String username = "";
            String password = "";
            
            switch (option) {
                case 1:
                    // Handles login
                    System.out.print("Enter username: ");
                    String loginUsername = frontendConnector.readTextInput();

                    System.out.print("Enter password: ");
                    String loginPassword = frontendConnector.readTextInput();

                    if (accountManager.signIn(loginUsername, loginPassword)) {
                        authenticated = true;
                        currentUsername = loginUsername;
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Login failed. Check your username or password.");
                    }
                    break;

                case 2:
                    // Handles account creation
                    System.out.print("Enter username: ");
                    username = frontendConnector.readTextInput();

                    System.out.print("Enter password: ");
                    password = frontendConnector.readTextInput();

                    System.out.print("Enter secret question: ");
                    String secretQuestion = frontendConnector.readTextInput();

                    System.out.print("Enter secret answer: ");
                    String secretAnswer = frontendConnector.readTextInput();

                    boolean accountCreated = accountManager.createAccount(
                        username,
                        password,
                        secretQuestion,
                        secretAnswer
                    );

                    if (accountCreated) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account could not be created.");
                    }
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
                    System.out.print("Enter username: ");
                    String resetUsername = frontendConnector.readTextInput();

                    System.out.print("Enter secret answer: ");
                    String resetSecretAnswer = frontendConnector.readTextInput();

                    System.out.print("Enter new password: ");
                    String resetNewPassword = frontendConnector.readTextInput();

                    if (accountManager.resetPasswordBySecretQuestion(
                            resetUsername,
                            resetSecretAnswer,
                            resetNewPassword)) {
                        System.out.println("Password reset successfully.");
                    } else {
                        System.out.println("Password reset failed.");
                    }
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
 * @author Mohamed Reda
 */
private void handleYearDataMenu() {
    boolean inYearDataMenu = true;

    while (inYearDataMenu) {
        int option = frontendConnector.showYearDataMenu();

        switch (option) {
            case 1:
                viewLoadedYearData();
                break;

            case 2:
                deleteYearData();
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
 * Displays all yearly CSV files saved for the currently logged-in user.
 *
 * @author Mohamed Reda
 */
private void viewLoadedYearData() {
    try {
        if (currentUsername == null || currentUsername.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if (!StorageService.isUserStorageSpaceExists(currentUsername)) {
            System.out.println("No saved yearly data found for this user.");
            return;
        }

        String userStoragePath = FileFolderManager.combinePaths(
            StorageService.BASE_STORAGE_PATH,
            currentUsername
        );

        String[] files = FileFolderManager.listFilesInFolder(userStoragePath);

        boolean foundYearData = false;

        System.out.println("\nLoaded yearly data:");

        for (String fileName : files) {
            if (fileName.endsWith(".csv")) {
                String year = fileName.substring(0, fileName.length() - 4);
                System.out.println("- " + year);
                foundYearData = true;
            }
        }

        if (!foundYearData) {
            System.out.println("No yearly CSV files are currently saved.");
        }

    } catch (Exception exception) {
        System.out.println(handleException(exception));
    }
}

/**
 * Deletes a yearly CSV file for the currently logged-in user.
 *
 * @author Mohamed Reda
 */
private void deleteYearData() {
    try {
        if (currentUsername == null || currentUsername.isEmpty()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if (!StorageService.isUserStorageSpaceExists(currentUsername)) {
            System.out.println("No saved yearly data found for this user.");
            return;
        }

        System.out.print("Enter the year data you want to delete: ");
        String yearInput = frontendConnector.readTextInput();

        if (!isValidYear(yearInput)) {
            System.out.println("Invalid year. Please enter a 4-digit year.");
            return;
        }

        String fileName = yearInput + ".csv";

        String userStoragePath = FileFolderManager.combinePaths(
            StorageService.BASE_STORAGE_PATH,
            currentUsername
        );

        String yearlyFilePath = FileFolderManager.combinePaths(
            userStoragePath,
            fileName
        );

        if (!FileFolderManager.isFileExists(yearlyFilePath)) {
            System.out.println("No saved data found for year " + yearInput + ".");
            return;
        }

        FileFolderManager.deleteFile(yearlyFilePath);

        System.out.println("Year data for " + yearInput + " was deleted successfully.");

    } catch (Exception exception) {
        System.out.println(handleException(exception));
    }
}

/**
 * Checks if the user entered a valid 4-digit year.
 *
 * @param yearInput the year entered by the user
 * @return true if the input is a valid year; false otherwise
 * @author Mohamed Reda
 */
private boolean isValidYear(String yearInput) {
    if (yearInput == null || yearInput.length() != 4) {
        return false;
    }

    for (int i = 0; i < yearInput.length(); i++) {
        if (!Character.isDigit(yearInput.charAt(i))) {
            return false;
        }
    }

    int year = Integer.parseInt(yearInput);

    return year >= 1900 && year <= 2100;
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
                    // Change password
                    System.out.print("Enter current password: ");
                    String oldPassword = frontendConnector.readTextInput();

                    System.out.print("Enter new password: ");
                    String newPassword = frontendConnector.readTextInput();

                    if (accountManager.changePassword(currentUsername, oldPassword, newPassword)) {
                        System.out.println("Password changed successfully.");
                    } else {
                        System.out.println("Password could not be changed.");
                    }
                
                    break;

                case 2:
                    // Update secret question
                    System.out.print("Enter new secret question: ");
                    String newQuestion = frontendConnector.readTextInput();

                    System.out.print("Enter new secret answer: ");
                    String newAnswer = frontendConnector.readTextInput();

                    if (accountManager.updateAccount(currentUsername, newQuestion, newAnswer)) {
                        System.out.println("Secret question updated successfully.");
                    } else {
                        System.out.println("Secret question could not be updated.");
                    }
                    break;

                case 3:
                    // Delete account
                    System.out.print("Enter password to confirm account deletion: ");
                    String deletePassword = frontendConnector.readTextInput();

                    if (accountManager.deleteAccount(currentUsername, deletePassword)) {
                        System.out.println("Account deleted successfully.");
                        currentUsername = "";
                        inAccountSettings = false;
                    } else {
                        System.out.println("Account could not be deleted.");
                    }
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
