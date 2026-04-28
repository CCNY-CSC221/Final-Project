package Integration;

/**
 * Gets user input from the menu and sends requests.
 *
 * @author Dmytro Shumlianskyi
 */
public class FrontendConnector {

    /**
     * Creates a FrontendConnector object.
     *
     * @author Dmytro Shumlianskyi
     */
    public FrontendConnector() {
    }

    /**
     * Checks user menu input.
     *
     * @param input the user input
     * @return true if input is valid; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean receiveUserInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        try {
            int option = Integer.parseInt(input);

            return option >= 1 && option <= 6;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    /**
     * Sends a user request to the correct module.
     *
     * @param requestType the request type
     * @return true if request is sent correctly; false otherwise
     * @author Dmytro Shumlianskyi
     */
    public boolean sendUserRequest(String requestType) {
        if (requestType == null || requestType.trim().isEmpty()) {
            return false;
        }

        // PLACEHOLDER:
        // These request names are temporary until real module calls are connected.
        // Later, each case should call the correct module or DataFlowController.
        switch (requestType.toLowerCase()) {
            case "load":
                return true; // TODO: connect to Validation/Storage workflow

            case "report":
                return true; // TODO: connect to Reports module

            case "insight":
                return true; // TODO: connect to Insights module

            case "audit":
                return true; // TODO: connect to Data Audit module

            case "delete":
                return true; // TODO: connect to Storage delete workflow

            case "exit":
                return true; // TODO: connect to clean program exit

            default:
                return false;
        }
    }
}