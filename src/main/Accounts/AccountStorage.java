package Accounts;

import java.io.*;

/**
 * AccountStorage class handles persistence operations for user accounts.
 * This class is responsible for saving and loading account data to/from files,
 * as well as managing password security through obfuscation.
 */
public class AccountStorage {

    /**
     * Saves an account to a file.
     * Persists the account information to the file system for long-term storage.
     * @param account the Accounts object to be saved
     * @param filename the name of the file where the account will be saved
     * @return true if the account is successfully saved to file, false otherwise
     */
    public boolean saveAccountToFile(Accounts account, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(account.getUsername());
            writer.newLine();

            // Store password in obfuscated form
            writer.write(obfuscatePassword(account.getPassword()));
            writer.newLine();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads an account from a file.
     * Retrieves account information from the file system.
     * @param filename the name of the file from which to load the account
     * @return an Accounts object loaded from the file, or null if the file is not found or cannot be read
     */
    public Accounts loadAccountFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String username = reader.readLine();
            String storedPassword = reader.readLine();

            if (username == null || storedPassword == null) {
                return null;
            }

            // Password remains obfuscated unless you want to decode it
            return new Accounts(username, storedPassword);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Obfuscates a password for secure storage.
     * Converts a plain-text password into an obfuscated/encrypted form to prevent unauthorized access.
     *
     * @param password the plain-text password to obfuscate
     * @return the obfuscated password as a String
     */
    public String obfuscatePassword(String password) {
        return ""; // Implementation WIP
    }
}
