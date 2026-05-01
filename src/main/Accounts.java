import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Accounts class handles user account management for the PFM application.
 *
 * @author Jin Chao Chen, Ayman Ahsan, Samuel Dewangga, Anita Nam
 */
public class Accounts {

    private String username;
    private String password;
    private String secretQuestion;
    private String secretAnswer;
    private boolean signedIn;

    private static List<Accounts> accounts = new ArrayList<>();

    /**
     * Default constructor
     */
    public Accounts(boolean skipLoad) {
        if (!skipLoad) {
            loadExistingAccounts();
        }
    }

    /**
     * Constructor for creating an account object
     */
    public Accounts(String username, String password,
                    String secretQuestion, String secretAnswer) {
        this.username = username;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
        this.signedIn = false;
    }

    /**
     * Creates a new user account.
     *
     * @return true if account was successfully created, false otherwise
     * @author Samuel Dewangga
     */
    public boolean createAccount(String username, String password,
                                 String secretQuestion, String secretAnswer) {
        if (username == null || username.isBlank() ||
            password == null || password.isBlank() ||
            secretQuestion == null || secretQuestion.isBlank() ||
            secretAnswer == null || secretAnswer.isBlank()) {
            return false;
        }
        AccountStorage storage = new AccountStorage();
        if (storage.loadAccountFromFile(username) != null) {
            return false;
        }

        // Create new account object (constructor remains thin)
        Accounts newAccount = new Accounts(username, password,
                                           secretQuestion, secretAnswer);

        // Encode password before saving and storing in memory
        newAccount.setPassword(storage.obfuscatePassword(password));

        if (storage.saveAccountToFile(newAccount, username)) {
            accounts.add(newAccount);
            return true;
        }
        return false;
    }

    /**
     * Deletes an existing user account and all associated data.
     *
     * @return true if account was successfully deleted, false otherwise
     * @author Samuel Dewangga
     */
    public boolean deleteAccount(String username, String password) {
        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            return false;
        }

        AccountStorage storage = new AccountStorage();
        Accounts existing = storage.loadAccountFromFile(username);

        if (existing == null) {
            return false;
        }

        if (!storage.obfuscatePassword(password)
                .equals(existing.getPassword())) {
            return false;
        }

        accounts.removeIf(account ->
                account.getUsername().equals(username));

        File file = new File("accounts/" + username + ".txt");
        if (file.exists()) {
            return file.delete();
        }

        return false;
    }

    /**
     * Reads and returns the account information for a given username.
     *
     * @param username the username to look up
     * @return the Accounts object, or null if not found
     * @author Anita Nam
     */
    public Accounts readAccount(String username) {
        for (Accounts account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Updates an existing user account's secret question or answer.
     * Password changes should be handled by changePassword() or resetPasswordBySecretQuestion()
     *
     * @param username    the username of the account to update
     * @param newQuestion the new secret question
     * @param newAnswer   the new secret answer
     * @return true if account was successfully updated, false otherwise
     * @author Anita Nam
     */
    public boolean updateAccount(String username,
                                 String newQuestion,
                                 String newAnswer) {

        if (username == null || username.isBlank() ||
            newQuestion == null || newQuestion.isBlank() ||
            newAnswer == null || newAnswer.isBlank()) {
            return false;
        }

        Accounts account = readAccount(username);
        if (account == null) {
            return false;
        }

        account.setSecretQuestion(newQuestion);
        account.setSecretAnswer(newAnswer);
        return true;
    }

    /**
     * Authenticates a user and allows them to sign in.
     *
     * @param username the username to authenticate
     * @param password the password to verify
     * @return true if authentication is successful, false otherwise
     * @author Jin Chao Chen
     */
    public boolean signIn(String username, String password) {

        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            return false;
        }

        AccountStorage storage = new AccountStorage();
        String encryptedPassword = storage.obfuscatePassword(password);

        for (Accounts account : accounts) {
            if (account.getUsername().equals(username) &&
                account.getPassword().equals(encryptedPassword)) {

                if (account.isSignedIn()) {
                    return false;
                }

                account.setSignedIn(true);
                return true;
            }
        }

        return false;
    }

    /**
     * Signs out a user from their account.
     *
     * @param username the username of the account to sign out
     * @return true if sign out is successful, false otherwise
     * @author Jin Chao Chen
     */
    public boolean signOut(String username) {

        if (username == null || username.isBlank()) {
            return false;
        }

        for (Accounts account : accounts) {
            if (account.getUsername().equals(username)) {

                if (!account.isSignedIn()) {
                    return false;
                }

                account.setSignedIn(false);
                return true;
            }
        }

        return false;
    }

    /**
     * Changes the password for an existing user account.
     *
     * @param username the username of the account
     * @param oldPassword the current password to verify identity
     * @param newPassword the new password to set
     * @return true if the password is successfully changed, false otherwise
     * @author Ayman Ahsan
     */
    public boolean changePassword(String username,
                                  String oldPassword,
                                  String newPassword) {
        if (username == null || username.isBlank() ||
            oldPassword == null || oldPassword.isBlank() ||
            newPassword == null || newPassword.isBlank()) {
            return false;
        }
        Accounts account = readAccount(username);
        if (account == null) {
            return false;
        }
        AccountStorage storage = new AccountStorage();
        if (!storage.obfuscatePassword(oldPassword)
                .equals(account.getPassword())) {   // Compare with encoded password
            return false;
        }
        account.setPassword(storage.obfuscatePassword(newPassword));  // Encode new password
        return true;
    }

    /**
     * Resets the password using a secret question answer.
     *
     * @param username the username of the account
     * @param secretAnswer the answer to the secret question
     * @param newPassword the new password to set
     * @return true if the password is successfully reset, false otherwise
     * @author Ayman Ahsan
     */
    public boolean resetPasswordBySecretQuestion(String username,
                                                 String secretAnswer,
                                                 String newPassword) {
        if (username == null || username.isBlank() ||
            secretAnswer == null || secretAnswer.isBlank() ||
            newPassword == null || newPassword.isBlank()) {
            return false;
        }
        if (!verifySecretAnswer(username, secretAnswer)) {
            return false;
        }
        Accounts account = readAccount(username);
        if (account == null) {
            return false;
        }
        AccountStorage storage = new AccountStorage();
        account.setPassword(storage.obfuscatePassword(newPassword));  // Encode new password
        return true;
    }

    /**
     * Verifies the secret answer for a given username.
     *
     * @param username     the username of the account
     * @param secretAnswer the answer to verify
     * @return true if the answer matches, false otherwise
     * @author Ayman Ahsan
     */
    public boolean verifySecretAnswer(String username,
                                      String secretAnswer) {

        if (username == null || username.isBlank() ||
            secretAnswer == null || secretAnswer.isBlank()) {
            return false;
        }

        Accounts account = readAccount(username);
        if (account == null) {
            return false;
        }

        return secretAnswer.equals(account.getSecretAnswer());
    }

/**
 * Loads all existing accounts from the accounts folder
 * into the static accounts ArrayList.
 *
 *@author Jin Chao Chen
 */
private void loadExistingAccounts() {
    File folder = new File("accounts/");

    // If folder doesn't exist, nothing to load
    if (!folder.exists() || !folder.isDirectory()) {
        return;
    }

    AccountStorage storage = new AccountStorage();
    File[] files = folder.listFiles();

    if (files == null) {
        return;
    }

    for (File file : files) {
        // Only process .txt account files
        if (file.isFile() && file.getName().endsWith(".txt")) {
            String filename = file.getName().replace(".txt", "");

            Accounts loadedAccount = storage.loadAccountFromFile(filename);

            if (loadedAccount != null) {
                // Prevent duplicates if constructor runs multiple times
                boolean exists = false;

                for (Accounts account : accounts) {
                    if (account.getUsername().equals(loadedAccount.getUsername())) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    accounts.add(loadedAccount);
                }
            }
        }
    }
}
    // Getters

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSecretQuestion() { return secretQuestion; }
    public String getSecretAnswer() { return secretAnswer; }
    public boolean isSignedIn() { return signedIn; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setSecretQuestion(String q) { this.secretQuestion = q; }
    public void setSecretAnswer(String a) { this.secretAnswer = a; }
    public void setSignedIn(boolean signedIn) { this.signedIn = signedIn; }
}
