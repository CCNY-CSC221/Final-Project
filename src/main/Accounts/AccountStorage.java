package Accounts;
import java.io.*;
import java.util.Base64;
/**
 * AccountStorage class handles persistence operations for user accounts.
 * Responsible for saving and loading account data to/from files,
 * as well as managing password security through obfuscation.
 *
 * @author Ayman Ahsan
 */
public class AccountStorage {
    private static final String ACCOUNTS_DIR = "accounts/";
    /**
     * Saves an account to a file.
     *
     * @param account  the Accounts object to be saved
     * @param filename the name of the file where the account will be saved
     * @return true if saved successfully, false otherwise
     * @author Ayman Ahsan
     */
    public boolean saveAccountToFile(Accounts account, String filename) {
        // Create accounts directory if it doesn't exist
        File dir = new File(ACCOUNTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(ACCOUNTS_DIR + filename + ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(account.getUsername());
            writer.println(account.getPassword());
            writer.println(account.getSecretQuestion());
            writer.println(account.getSecretAnswer());
            return true;
        } catch (IOException e) {
            System.out.println("Error: Could not save account to file.");
            return false;
        }
    }
    /**
     * Loads an account from a file.
     *
     * @param filename the name of the file to load from
     * @return an Accounts object, or null if not found
     * @author Ayman Ahsan
     */
    public Accounts loadAccountFromFile(String filename) {
        File file = new File(ACCOUNTS_DIR + filename + ".txt");
        if (!file.exists()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String username      = reader.readLine();
            String password      = reader.readLine();
            String secretQuestion = reader.readLine();
            String secretAnswer  = reader.readLine();
            if (username == null || password == null ||
                secretQuestion == null || secretAnswer == null) {
                return null;
            }
            Accounts account = new Accounts();
            account.setUsername(username);
            account.setPassword(password);
            account.setSecretQuestion(secretQuestion);
            account.setSecretAnswer(secretAnswer);
            return account;
        } catch (IOException e) {
            System.out.println("Error: Could not load account from file.");
            return null;
        }
    }
    /**
     * Obfuscates a password for secure storage using Base64 encoding.
     *
     * @param password the plain-text password
     * @return the obfuscated password as a String
     * @author Ayman Ahsan
     */
    public String obfuscatePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
