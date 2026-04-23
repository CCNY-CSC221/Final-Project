import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a ledger of transactions.
 */
public class TransactionLedger implements Comparable<TransactionLedger> {

    /**
     * Creates ledger from list of transactions.
     *
     * @param transactions list of transactions
     */
    public TransactionLedger(ArrayList<Transaction> transactions) {}

    /**
     * Loads ledger from CSV file.
     *
     * @param filePath file path
     * @throws IOException if file cannot be read
     */
    public TransactionLedger(String filePath) @throws IOException{}

    /**
     * Parses CSV text into a ledger.
     *
     * @param text CSV text
     * @return TransactionLedger object
     */
    public static TransactionLedger createFromCSVText(String text) {}

    /**
     * Converts ledger to CSV text.
     *
     * @return CSV string
     */
    public String transformToCSVText() {}

    /** @return transaction list */
    public ArrayList<Transaction> getTransactions() {}

    /**
     * @param transactions new transaction list
     */
    public void setTransactions(ArrayList<Transaction> transactions) {}

    /**
     * Adds a transaction.
     *
     * @param transaction transaction to add
     * @throws IllegalArgumentException if null or duplicate
     */
    public void addTransaction(Transaction transaction) {}

    /**
     * Removes a transaction.
     *
     * @param transaction transaction to remove
     * @throws IllegalArgumentException if not found
     */
    public void delTransaction(Transaction transaction) {}

    /** @return total income */
    public float getTotalIncome() {}

    /** @return total expense */
    public float getTotalExpense() {}

    /**
     * @param month month (1–12)
     * @return net income for month
     */
    public float getMonthlyNetIncome(int month) {}

    /** @return category totals map */
    public Map<String, Float> getCategoryTotals() {}

    /**
     * Compares ledgers by size.
     *
     * @param other other ledger
     * @return comparison result
     */
    @Override
    public int compareTo(TransactionLedger other) {}
}
