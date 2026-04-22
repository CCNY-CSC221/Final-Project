import java.util.Map;

/**
 * Stores user financial data and yearly ledgers.
 */
public class UserStorage implements Comparable<UserStorage> {

    /**
     * Creates user storage.
     *
     * @param userName username
     */
    public UserStorage(String userName) {}

    /** @return username */
    public String getUserName() {}

    /** @return yearly ledgers */
    public Map<Integer, TransactionLedger> getYearlyLedgers() {}

    /**
     * @param year year
     * @return ledger for year or null
     */
    public TransactionLedger getLedgerByYear(int year) {}

    /**
     * @param year year
     * @return true if ledger exists
     */
    public boolean hasLedgerForYear(int year) {}

    /**
     * Adds or replaces a ledger.
     *
     * @param ledger ledger to add
     */
    public void addLedger(TransactionLedger ledger) {}

    /**
     * Removes ledger for a year.
     *
     * @param year year
     */
    public void delLedger(int year) {}

    /**
     * Compares users by name.
     *
     * @param other other user
     * @return comparison result
     */
    @Override
    public int compareTo(UserStorage other) {}
}