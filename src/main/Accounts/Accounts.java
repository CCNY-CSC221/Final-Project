package Accounts;

/**
 * Accounts class manages user account operations including creation, authentication,
 * and account modifications. This class serves as the primary interface for account
 * management functionality within the system.
 */
public class Accounts {

        // Array of predefined secret questions
        private static final String[] SECRET_QUESTIONS = {
            "What is your mother's maiden name?",
            "What was the name of your first pet?",
            "What city were you born in?",
            "What is your favorite book?",
            "What is the name of the street you grew up on?",
            "What was the make of your first car?",
            "What is your favorite movie?",
            "What is your childhood nickname?",
            "What school did you attend?",
            "What is your favorite food?"
        };

        private String username;
        private String password;
        private String secretQuestion;
        private String secretAnswer;

        /**
        * Creates an Accounts instance.
        */
        public Accounts() {
        }

        /**
        * Creates a new user account.
        *
        * @param username the desired username
        * @param password the desired password
        * @param secretQuestion a random security question for password recovery
        * @param secretAnswer the answer to the security question
        * @return true if account creation is successful, false otherwise
        */
        public boolean createAccount(String username, String password, String secretQuestion, String secretAnswer) {
            return true; // Implementation WIP
        }

        /**
        * Deletes an existing user account.
        * @param username the username of the account to delete
        * @param password the password of the account to confirm identity
        * @return true if account deletion is successful, false otherwise
        */
        public boolean deleteAccount(String username, String password) {
            return true; // Implementation WIP
        }

        /**
         * Retrieves account information for a given username.
         * @param username the username of the account to read
         * @return an Accounts object containing the account information, or null if not found
         */
        public Accounts readAccount(String username) {
            return null; // Implementation WIP
        }

        /**
         * Updates the password for an existing user account.
         * @param username the username of the account to update
         * @param currPassword the current password to verify identity
         * @param newPassword the new password to set
         * @return true if the password is successfully updated, false otherwise
         */
        public boolean updateAccount(String username, String currPassword,String newPassword) {
            return true; // Implementation WIP
        }

        /**
         * Authenticates a user and allows them to sign in.
         * @param username the username to authenticate
         * @param password the password to verify
         * @return true if authentication is successful, false otherwise
         */
        public boolean signIn(String username, String password) {
            return true; // Implementation WIP
        }

        /**
         * Signs out a user from their account.
         * @param username the username of the account to sign out
         * @return true if sign out is successful, false otherwise
         */
        public boolean signOut(String username) {
            return true; // Implementation WIP
        }

        /**
         * Changes the password for an existing user account.
         * @param username the username of the account
         * @param oldPassword the current password to verify identity
         * @param newPassword the new password to set
         * @return true if the password is successfully changed, false otherwise
         */
        public boolean changePassword(String username, String oldPassword, String newPassword) {
                // Validate input parameters
                if (username == null || username.isBlank() || oldPassword == null || oldPassword.isBlank() || newPassword == null || newPassword.isBlank()) {
                    return false; // Invalid input
                }

                // Verify the old password
                if (!signIn(username, oldPassword)) {
                    return false; // Authentication failed
                }

                // Update the password
                return updateAccount(username, oldPassword, newPassword);
        }

        /**
         * Resets the password for an existing user account using a secret question.
         * Provides an alternative password recovery mechanism when the user forgets their password.
         * Verifies the provided secret answer before allowing password reset.
         * @param username the username of the account
         * @param email the email address associated with the account for verification
         * @param secretAnswer the answer to the secret question for verification
         * @param newPassword the new password to set
         * @return true if the password is successfully reset, false otherwise
         */
        public boolean resetPasswordBySecretQuestion(String username, String email, String secretAnswer, String newPassword) {
            // Validate input parameters
            if (username == null || username.isBlank() || email == null || email.isBlank() || secretAnswer == null || secretAnswer.isBlank() || newPassword == null || newPassword.isBlank()) {
                return false; // Invalid input
            }
            // Verify the secret answer
            return verifySecretAnswer(username, secretAnswer) && updateAccount(username, password, newPassword);
        }

        public boolean verifySecretAnswer(String username, String secretAnswer) {
            // Input validation
            if (username == null || username.isBlank() || secretAnswer == null || secretAnswer.isBlank()) {
                return false; // Invalid input
            }
            // Check if the provided secret answer matches the stored answer for the user
            Accounts account = readAccount(username);
            if (account == null) {
                    return false; // Account not found
            }
            return secretAnswer.equals(account.secretAnswer);
        }

}
