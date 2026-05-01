import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static int totalTests = 0;
    private static int passedTests = 0;

    // Shared state for manual mode
    private static UserStorage currentUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Run automated tests
        System.out.println("=== STORAGE SERVICE SYSTEM TEST START ===\n");

        testTransaction();
        testTransactionLedger();
        testUserStorageAndService();
        testFileFolderManager();
       

        System.out.println("\n=== FINAL SUMMARY ===");
        System.out.println("Passed: " + passedTests + " / " + totalTests);
        if (totalTests == passedTests) {
            System.out.println("ALL TESTS PASSED");
        } else {
            System.out.println("SOME TESTS FAILED");
        }

        System.out.println("\nNOTE: Test user was NOT deleted.");
        
        // Ask if user wants to enter manual mode
        System.out.print("\nDo you want to enter manual testing mode? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            manualMode();
        } else {
            System.out.println("Exiting program. Goodbye!");
        }
        scanner.close();
    }

    // ==================== AUTOMATED TESTS ====================
    private static void testTransaction() {
        System.out.println("--- 1. Testing Transaction ---");
        boolean passed = true;
        try {
            Transaction t1 = new Transaction(
                LocalDate.of(2025, 3, 10),
                "Groceries",
                "Supermarket purchase",
                2500.50f,
                "expense"
            );
            Transaction t2 = new Transaction(
                LocalDate.of(2025, 3, 15),
                "Salary",
                "Monthly advance",
                50000.00f,
                "income"
            );
            Transaction t3 = new Transaction(
                LocalDate.of(2025, 3, 20),
                "Entertainment",
                "Cinema",
                800.00f,
                "expense"
            );

            System.out.println("Created transactions:");
            System.out.println("  " + t1.getDate() + " | " + t1.getCategory() + " | " + t1.getAmount() + " | " + t1.getType());
            System.out.println("  " + t2.getDate() + " | " + t2.getCategory() + " | " + t2.getAmount() + " | " + t2.getType());
            System.out.println("  " + t3.getDate() + " | " + t3.getCategory() + " | " + t3.getAmount() + " | " + t3.getType());

            Transaction t1copy = new Transaction(t1);
            if (!t1.equals(t1copy)) throw new AssertionError("Copy not equal to original");
            if (t1.equals(t2)) throw new AssertionError("Different transactions considered equal");

            String csvLine = t1.transformToCSVText().trim();
            Transaction parsed = Transaction.createFromCSVText(csvLine);
            if (!t1.equals(parsed)) throw new AssertionError("CSV parse error");

            System.out.println("CSV line of t1: " + csvLine);
            System.out.println("Parsed transaction equals original? " + t1.equals(parsed));
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            passed = false;
        }
        totalTests++;
        if (passed) {
            System.out.println("[Transaction] - PASSED\n");
            passedTests++;
        } else {
            System.out.println("[Transaction] - FAILED\n");
        }
    }

    private static void testTransactionLedger() {
        System.out.println("--- 2. Testing TransactionLedger ---");
        boolean passed = true;
        try {
            Transaction t1 = new Transaction(LocalDate.of(2025, 3, 10), "Groceries", "Supermarket purchase", 2500.50f, "expense");
            Transaction t2 = new Transaction(LocalDate.of(2025, 3, 15), "Salary", "Monthly advance", 50000.00f, "income");
            Transaction t3 = new Transaction(LocalDate.of(2025, 3, 20), "Entertainment", "Cinema", 800.00f, "expense");

            TransactionLedger ledger = new TransactionLedger();
            ledger.addTransaction(t1);
            ledger.addTransaction(t2);
            ledger.addTransaction(t3);

            float totalIncome = ledger.getTotalIncome();
            float totalExpense = ledger.getTotalExpense();
            float netMarch = ledger.getMonthlyNetIncome(3);

            if (Math.abs(totalIncome - 50000.0f) > 0.001) throw new AssertionError("Wrong total income");
            if (Math.abs(totalExpense - 3300.5f) > 0.001) throw new AssertionError("Wrong total expense");
            if (Math.abs(netMarch - 46699.5f) > 0.001) throw new AssertionError("Wrong net income");

            Map<String, Float> catTotals = ledger.getCategoryTotals();
            if (catTotals.get("Salary") != 50000.0f) throw new AssertionError("Category Salary incorrect");
            if (catTotals.get("Groceries") != -2500.5f) throw new AssertionError("Category Groceries incorrect");
            if (catTotals.get("Entertainment") != -800.0f) throw new AssertionError("Category Entertainment incorrect");

            TransactionLedger ledgerCopy = new TransactionLedger(ledger);
            if (!ledger.equals(ledgerCopy)) throw new AssertionError("Copy not equal");
            if (ledger.compareTo(ledgerCopy) != 0) throw new AssertionError("CompareTo failed");

            System.out.println("Total income: " + totalIncome);
            System.out.println("Total expense: " + totalExpense);
            System.out.println("Net income for March: " + netMarch);
            System.out.println("Category totals:");
            for (Map.Entry<String, Float> e : catTotals.entrySet()) {
                System.out.println("  " + e.getKey() + " -> " + e.getValue());
            }
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            passed = false;
        }
        totalTests++;
        if (passed) {
            System.out.println("[TransactionLedger] - PASSED\n");
            passedTests++;
        } else {
            System.out.println("[TransactionLedger] - FAILED\n");
        }
    }

    private static void testUserStorageAndService() {
        System.out.println("--- 3. Testing UserStorage and StorageService ---");
        boolean passed = true;
        String testUserName = "Vikhor_Vilcynsky_Kozak";
        try {
            // Ensure base folder exists
            if (!FileFolderManager.isFolderExists(StorageService.BASE_STORAGE_PATH)) {
                FileFolderManager.createFolder(StorageService.BASE_STORAGE_PATH);
                System.out.println("Created base storage folder: " + StorageService.BASE_STORAGE_PATH);
            }

            // Clean previous user if exists (optional – we keep for clarity)
            if (StorageService.isUserStorageSpaceExists(testUserName)) {
                StorageService.deleteUserStorageSpace(testUserName);
                System.out.println("Deleted old user profile (clean test).");
            }

            StorageService.createUserStorageSpace(testUserName);
            System.out.println("Created storage for user: " + testUserName);

            // Create test transactions and ledger
            Transaction t1 = new Transaction(LocalDate.of(2025, 3, 10), "Groceries", "Supermarket", 2500.50f, "expense");
            Transaction t2 = new Transaction(LocalDate.of(2025, 3, 15), "Salary", "Monthly", 50000.00f, "income");
            TransactionLedger ledger2025 = new TransactionLedger();
            ledger2025.addTransaction(t1);
            ledger2025.addTransaction(t2);

            Transaction t3 = new Transaction(LocalDate.of(2024, 12, 31), "Gift", "Bonus", 15000.00f, "income");
            TransactionLedger ledger2024 = new TransactionLedger();
            ledger2024.addTransaction(t3);

            UserStorage user = new UserStorage(testUserName);
            user.addLedger(ledger2025);
            user.addLedger(ledger2024);

            StorageService.saveUserStorage(user);
            System.out.println("User successfully saved to disk.");

            // --- NEW: Check files exist after save ---
            String userFolderPath = FileFolderManager.combinePaths(StorageService.BASE_STORAGE_PATH, testUserName);
            String[] files = FileFolderManager.listFilesInFolder(userFolderPath);
            System.out.println("Files in user folder (" + userFolderPath + "):");
            if (files.length == 0) {
                System.out.println("  NO FILES FOUND!");
                throw new AssertionError("No CSV files created after saveUserStorage");
            } else {
                for (String f : files) {
                    System.out.println("  - " + f);
                }
            }
            // Also show absolute path
            Path absPath = Paths.get(userFolderPath).toAbsolutePath();
            System.out.println("Absolute path: " + absPath);

            UserStorage loadedUser = StorageService.loadUserStorage(testUserName);
            System.out.println("User successfully loaded from disk.");

            // Compare original and loaded
            if (!user.getUserName().equals(loadedUser.getUserName()))
                throw new AssertionError("User names differ");
            Map<Integer, TransactionLedger> origLedgers = user.getYearlyLedgers();
            Map<Integer, TransactionLedger> loadedLedgers = loadedUser.getYearlyLedgers();
            if (origLedgers.size() != loadedLedgers.size())
                throw new AssertionError("Number of yearly ledgers differ");
            for (Integer year : origLedgers.keySet()) {
                if (!origLedgers.get(year).equals(loadedLedgers.get(year)))
                    throw new AssertionError("Ledger for year " + year + " differs");
            }
            System.out.println("Original and loaded user are identical? true");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            passed = false;
        }
        totalTests++;
        if (passed) {
            System.out.println("[UserStorage & StorageService] - PASSED\n");
            passedTests++;
        } else {
            System.out.println("[UserStorage & StorageService] - FAILED\n");
        }
    }

    private static void testFileFolderManager() {
        System.out.println("--- 4. Testing FileFolderManager ---");
        boolean passed = true;
        String testDir = "TestFolder";
        String testFile = "test.txt";
        try {
            // Create folder
            if (!FileFolderManager.isFolderExists(testDir)) {
                FileFolderManager.createFolder(testDir);
                System.out.println("Created test folder: " + testDir);
            }
            // Create file
            String filePath = FileFolderManager.combinePaths(testDir, testFile);
            if (!FileFolderManager.isFileExists(filePath)) {
                FileFolderManager.createFile(filePath);
                System.out.println("Created test file: " + filePath);
            }
            // Write and read
            String testContent = "Hello, FileSystem!\nSecond line.";
            FileFolderManager.writeFile(filePath, testContent);
            String readContent = FileFolderManager.readFile(filePath);
            if (!testContent.equals(readContent))
                throw new AssertionError("Written and read content do not match");
            System.out.println("Written and read content match? true");

            String[] files = FileFolderManager.listFilesInFolder(testDir);
            System.out.print("Files in folder '" + testDir + "': ");
            for (String f : files) System.out.print(f + " ");
            System.out.println();

            // Cleanup
            FileFolderManager.deleteFile(filePath);
            FileFolderManager.deleteFolder(testDir);
            System.out.println("Test file and folder deleted.");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            passed = false;
        }
        totalTests++;
        if (passed) {
            System.out.println("[FileFolderManager] - PASSED\n");
            passedTests++;
        } else {
            System.out.println("[FileFolderManager] - FAILED\n");
        }
    }

    // ==================== MANUAL MODE ====================
    private static void manualMode() {
        System.out.println("\n=== MANUAL TESTING MODE ===");
        System.out.println("You can interact with the storage system.");
        boolean exit = false;

        while (!exit) {
            if (currentUser == null) {
                System.out.println("\nNo user currently loaded.");
                System.out.println("1. Create new user");
                System.out.println("2. Load existing user");
                System.out.println("3. Exit manual mode");
                System.out.print("Choose option: ");
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        createNewUser();
                        break;
                    case "2":
                        loadExistingUser();
                        break;
                    case "3":
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                continue;
            }

            System.out.println("\n=== User: " + currentUser.getUserName() + " ===");
            System.out.println("1. Add transaction to current year (or specified year)");
            System.out.println("2. View ledger for a specific year");
            System.out.println("3. View totals (income/expense/net) for a year");
            System.out.println("4. View category totals for a year");
            System.out.println("5. Save current user to disk");
            System.out.println("6. Reload current user from disk (discard unsaved changes)");
            System.out.println("7. Switch to another user");
            System.out.println("8. Delete current user");
            System.out.println("9. Exit manual mode");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        addTransaction();
                        break;
                    case "2":
                        viewLedger();
                        break;
                    case "3":
                        viewTotals();
                        break;
                    case "4":
                        viewCategoryTotals();
                        break;
                    case "5":
                        saveCurrentUser();
                        break;
                    case "6":
                        reloadCurrentUser();
                        break;
                    case "7":
                        switchUser();
                        break;
                    case "8":
                        deleteCurrentUser();
                        break;
                    case "9":
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Operation failed: " + e.getMessage());
            }
        }
        System.out.println("Exiting manual mode.");
    }

    private static void createNewUser() {
        System.out.print("Enter new username: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        try {
            StorageService.createUserStorageSpace(name);
            currentUser = new UserStorage(name);
            System.out.println("User '" + name + "' created and loaded.");
        } catch (Exception e) {
            System.err.println("Failed to create user: " + e.getMessage());
        }
    }

    private static void loadExistingUser() {
        System.out.print("Enter username to load: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        try {
            if (!StorageService.isUserStorageSpaceExists(name)) {
                System.out.println("User '" + name + "' does not exist.");
                return;
            }
            currentUser = StorageService.loadUserStorage(name);
            System.out.println("User '" + name + "' loaded successfully.");
        } catch (Exception e) {
            System.err.println("Failed to load user: " + e.getMessage());
        }
    }

    private static void addTransaction() {
        System.out.println("Add a new transaction");
        try {
            System.out.print("Year (YYYY): ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Day (1-31): ");
            int day = Integer.parseInt(scanner.nextLine().trim());
            LocalDate date = LocalDate.of(year, month, day);

            System.out.print("Category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Description: ");
            String description = scanner.nextLine().trim();
            System.out.print("Amount (positive float): ");
            float amount = Float.parseFloat(scanner.nextLine().trim());
            System.out.print("Type (income/expense): ");
            String type = scanner.nextLine().trim().toLowerCase();

            Transaction t = new Transaction(date, category, description, amount, type);

            // Get the ledger for the specific year (or create new)
            TransactionLedger ledger;
            if (!currentUser.getYearlyLedgers().containsKey(year)) {
                ledger = new TransactionLedger();
                ledger.addTransaction(t);
                currentUser.addLedger(ledger);
            } else {
                // getYearlyLedgers() returns a COPY of the map, but we can still fetch the copy
                // and then replace it using addLedger.
                ledger = currentUser.getYearlyLedgers().get(year);
                ledger.addTransaction(t);
                currentUser.addLedger(ledger); // replace the ledger for that year
            }
            System.out.println("Transaction added successfully.");
        } catch (Exception e) {
            System.err.println("Invalid input or transaction error: " + e.getMessage());
        }
    }

    private static void viewLedger() {
        System.out.print("Enter year to view: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        TransactionLedger ledger = currentUser.getLedgerByYear(year);
        if (ledger.getTransactions().isEmpty()) {
            System.out.println("No transactions for year " + year);
        } else {
            System.out.println("Transactions for " + year + ":");
            for (Transaction t : ledger.getTransactions()) {
                System.out.printf("  %s | %s | %s | %.2f | %s%n",
                    t.getDate(), t.getCategory(), t.getDescription(), t.getAmount(), t.getType());
            }
        }
    }

    private static void viewTotals() {
        System.out.print("Enter year to view totals: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        TransactionLedger ledger = currentUser.getLedgerByYear(year);
        float income = 0f, expense = 0f;
        for (Transaction t : ledger.getTransactions()) {
            if (t.getType().equals("income")) income += t.getAmount();
            else expense += t.getAmount();
        }
        float net = income - expense;
        System.out.printf("Year %d totals: Income = %.2f, Expense = %.2f, Net = %.2f%n", year, income, expense, net);
    }

    private static void viewCategoryTotals() {
        System.out.print("Enter year to view category totals: ");
        int year = Integer.parseInt(scanner.nextLine().trim());
        TransactionLedger ledger = currentUser.getLedgerByYear(year);
        Map<String, Float> totals = ledger.getCategoryTotals();
        if (totals.isEmpty()) {
            System.out.println("No categories for year " + year);
        } else {
            System.out.println("Category totals for " + year + ":");
            for (Map.Entry<String, Float> e : totals.entrySet()) {
                System.out.printf("  %s -> %.2f%n", e.getKey(), e.getValue());
            }
        }
    }

    private static void saveCurrentUser() {
        try {
            StorageService.saveUserStorage(currentUser);
            System.out.println("User '" + currentUser.getUserName() + "' saved to disk.");
        } catch (Exception e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    private static void reloadCurrentUser() {
        try {
            currentUser = StorageService.loadUserStorage(currentUser.getUserName());
            System.out.println("User reloaded from disk.");
        } catch (Exception e) {
            System.err.println("Reload failed: " + e.getMessage());
        }
    }

    private static void switchUser() {
        System.out.print("Switch to another user. Enter username: ");
        String name = scanner.nextLine().trim();
        try {
            if (!StorageService.isUserStorageSpaceExists(name)) {
                System.out.println("User does not exist. Create first? (yes/no)");
                String ans = scanner.nextLine().trim().toLowerCase();
                if (ans.equals("yes") || ans.equals("y")) {
                    StorageService.createUserStorageSpace(name);
                    currentUser = new UserStorage(name);
                    System.out.println("User created and switched to '" + name + "'.");
                } else {
                    System.out.println("Switch cancelled.");
                }
            } else {
                currentUser = StorageService.loadUserStorage(name);
                System.out.println("Switched to user '" + name + "'.");
            }
        } catch (Exception e) {
            System.err.println("Switch failed: " + e.getMessage());
        }
    }

    private static void deleteCurrentUser() {
        System.out.print("Are you sure you want to delete user '" + currentUser.getUserName() + "'? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("yes") || confirm.equals("y")) {
            try {
                StorageService.deleteUserStorageSpace(currentUser.getUserName());
                currentUser = null;
                System.out.println("User deleted.");
            } catch (Exception e) {
                System.err.println("Deletion failed: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}