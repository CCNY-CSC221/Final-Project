package Accounts;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
     * Creates a new user account.
     *
     * @return true if account was successfully created, false otherwise
     * @author Samuel Dewangga
     */
    public boolean createAccount() {
        Scanner scanner = new Scanner(System.in);
        AccountStorage storage = new AccountStorage();
        System.out.print("Enter a username: ");
        String inputUsername = scanner.nextLine().trim();
        if (storage.loadAccountFromFile(inputUsername) != null) {
            System.out.println("Error: Username already exists.");
            return false;
        }
        System.out.print("Enter a password: ");
        String inputPassword = scanner.nextLine().trim();
        System.out.print("Enter a secret question: ");
        String inputQuestion = scanner.nextLine().trim();
        System.out.print("Enter the answer: ");
        String inputAnswer = scanner.nextLine().trim();
        this.username = inputUsername;
        this.password = storage.obfuscatePassword(inputPassword);
        this.secretQuestion = inputQuestion;
        this.secretAnswer = inputAnswer;
        this.signedIn = false;
        if (storage.saveAccountToFile(this, inputUsername)) {
            accounts.add(this);
            System.out.println("Account created for '" + inputUsername + "'.");
            return true;
        }
        System.out.println("Error: Could not save account.");
        return false;
    }
    /**
     * Deletes an existing user account and all associated data.
     *
     * @return true if account was successfully deleted, false otherwise
     * @author Samuel Dewangga
     */
    public boolean deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        AccountStorage storage = new AccountStorage();
        System.out.print("Enter your username: ");
        String inputUsername = scanner.nextLine().trim();
        Accounts existing = storage.loadAccountFromFile(inputUsername);
        if (existing == null) {
            System.out.println("Error: Account not found.");
            return false;
        }
        System.out.print("Enter your password to confirm: ");
        String inputPassword = scanner.nextLine().trim();
        if (!storage.obfuscatePassword(inputPassword).equals(existing.getPassword())) {
            System.out.println("Error: Incorrect password.");
            return false;
        }
        accounts.remove(existing);
        System.out.println("Account '" + inputUsername + "' deleted.");
        return true;
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
    public boolean updateAccount(String username, String newQuestion, String newAnswer) {
        Accounts account = readAccount(username);
        if (account == null) return false;
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
        for (Accounts account : accounts) {
            if (account.getUsername().equals(username) &&
                account.getPassword().equals(password)) {
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
     * @param username    the username of the account
     * @param oldPassword the current password to verify identity
     * @param newPassword the new password to set
     * @return true if the password is successfully changed, false otherwise
     * @author Ayman Ahsan
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (username == null || username.isBlank() ||
            oldPassword == null || oldPassword.isBlank() ||
            newPassword == null || newPassword.isBlank()) {
        }
            return false;
        if (!signIn(username, oldPassword)) {
            return false;
        }
        Accounts account = readAccount(username);
        if (account == null) return false;
        AccountStorage storage = new AccountStorage();
        account.setPassword(storage.obfuscatePassword(newPassword));
        return true;
    }
    /**
     * Resets the password using a secret question answer.
     *
     * @param username     the username of the account
     * @param secretAnswer the answer to the secret question
     * @param newPassword  the new password to set
     * @return true if the password is successfully reset, false otherwise
     * @author Ayman Ahsan
     */
    public boolean resetPasswordBySecretQuestion(String username, String secretAnswer, String newPassword) {
        if (username == null || username.isBlank() ||
            secretAnswer == null || secretAnswer.isBlank() ||
            newPassword == null || newPassword.isBlank()) {
            return false;
        }
        if (!verifySecretAnswer(username, secretAnswer)) return false;
        Accounts account = readAccount(username);
        if (account == null) return false;
        AccountStorage storage = new AccountStorage();
        account.setPassword(storage.obfuscatePassword(newPassword));
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
    public boolean verifySecretAnswer(String username, String secretAnswer) {
        if (username == null || username.isBlank() ||
            secretAnswer == null || secretAnswer.isBlank()) {
            return false;
        }
        Accounts account = readAccount(username);
        if (account == null) return false;
        return secretAnswer.equals(account.getSecretAnswer());
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
