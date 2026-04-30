import java.util.Map;
import java.util.HashMap;

/**
 * Stores user financial data and yearly ledgers.
 */
public class UserStorage implements Comparable<UserStorage> {
    private String userName;
    private Map<Integer, TransactionLedger> yearLedgers;
    
    public UserStorage(String userName) {
        this userName = userName;
        this yearLedgers = new HashMap<>();
    }
    
    public String getUserName() {return userName;}

    public Map<Integer, TransactionLedger> getYearlyLedgers() { return yearLedgers;}

    public TransactionLedger getLedgerByYear(int year) {return yearLedgers.get(year);}

    public boolean hasLedgerForYear(int year) {return yearLedgers.containsKeys(year);}}

    public void addLedger(TransactionLedger ledger) {return yearLedgers.put(ledger.getYear(), ledger);}}

    public void delLedger(int year) { yearLedgers.remove(year);}}

    @Override
    public int compareTo(UserStorage other) {return this.userName.compareTo(other.userName);}
}
