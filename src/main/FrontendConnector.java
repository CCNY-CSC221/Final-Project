import java.util.Scanner;

/**
 * Handles menu display, user input, and menu validation.
 *
 * @author Dmytro Shumlianskyi
 */
public class FrontendConnector {

    private final Scanner scanner;

    /**
     * Creates a FrontendConnector object and prepares console input.
     *
     * @author Dmytro Shumlianskyi
     */
    public FrontendConnector() {
        scanner = new Scanner(System.in);
    }

    /**
     * Shows the authentication menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
    */
    public int showAuthenticationMenu() {
        System.out.println("\n===== Authentication Menu =====");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Forgot Password");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 4);
    }

    /**
     * Shows the forgot password menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
     */
    public int showForgotPasswordMenu() {
        System.out.println("\n===== Forgot Password =====");
        System.out.println("1. Reset Password by Secret Question");
        System.out.println("2. Back to Authentication Menu");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 2);
    }

    /**
     * Shows the main menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
    */
    public int showMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Load Yearly CSV");
        System.out.println("2. Manage Year Data");
        System.out.println("3. Generate Report");
        System.out.println("4. Run Insights");
        System.out.println("5. Run Data Audit");
        System.out.println("6. Account Settings");
        System.out.println("7. Sign Out");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 8);
    }

    /**
     * Shows the year data menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
     */
    public int showYearDataMenu() {
        System.out.println("\n===== Manage Year Data =====");
        System.out.println("1. View Saved Years");
        System.out.println("2. Delete Year Data");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 3);
    }

    /**
     * Shows the reports menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
     */
    public int showReportMenu() {
        System.out.println("\n===== Reports Menu =====");
        System.out.println("1. Annual Report");
        System.out.println("2. Category Report");
        System.out.println("3. Monthly Summary");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 4);
    }

    /**
     * Shows the insights menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
     */
    public int showInsightsMenu() {
        System.out.println("\n===== Insights Menu =====");
        System.out.println("1. Spending Percentage Breakdown");
        System.out.println("2. Deficit Suggestions");
        System.out.println("3. Export Insights to CSV");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 4);
    }

    /**
     * Shows the data audit menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
     */
    public int showDataAuditMenu() {
        System.out.println("\n===== Data Audit Menu =====");
        System.out.println("1. Run Audit for Year");
        System.out.println("2. Save Audit Report");
        System.out.println("3. Delete Old Audit Records");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 4);
    }


    /**
     * Shows the account settings menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
     */
    public int showAccountSettingsMenu() {
        System.out.println("\n===== Account Settings =====");
        System.out.println("1. Change Password");
        System.out.println("2. Update Secret Question");
        System.out.println("3. Delete Account");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 4);
    }


    /**
     * Reads a menu option and checks if it is inside the valid range.
     *
     * @param minOption the smallest valid option number
     * @param maxOption the largest valid option number
     * @return the selected option, or -1 if input is invalid
     * @author Dmytro Shumlianskyi
    */
    public int readMenuOption(int minOption, int maxOption) {
        String input = scanner.nextLine().trim();

        if (!isNumericInput(input)) {
            return -1;
        }

        int option = Integer.parseInt(input);
        
        if (option < minOption || option > maxOption) {
            return -1;
        }

        return option;
    }

    /**
     * Checks if user input is numeric.
     *
     * @param input the user input
     * @return true if the input is numeric; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean isNumericInput(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public String readTextInput() {
        return scanner.nextLine().trim();
    }
}
