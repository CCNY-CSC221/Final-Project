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
     * Shows the main menu and reads the user's choice.
     *
     * @return the selected menu option, or -1 if the input is invalid
     * @author Dmytro Shumlianskyi
    */
    public int showMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Load Yearly CSV");
        System.out.println("2. Generate Report");
        System.out.println("3. Run Insights");
        System.out.println("4. Run Data Audit");
        System.out.println("5. Delete Year Data");
        System.out.println("6. Account Settings");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");

        return readMenuOption(1, 7);
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
        return scanner.nextline().trim();
    }
}
