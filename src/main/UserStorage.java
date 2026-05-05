// @author Andrinho
// Import external dependencies
import java.util.HashMap;
import java.util.Map;



/**
 * Stores user financial data and yearly ledgers.
 */
public class UserStorage implements Comparable<UserStorage> {
	// Object fields 
	private String userName;
	private Map<Integer, TransactionLedger> yearlyLedgers;
	
	
    /**
     * Creates user storage without ledgers.
     *
     * @param userName user name
     */
    public UserStorage(String userName) {
    	this.setUserName(userName);
    	yearlyLedgers = new HashMap<Integer, TransactionLedger>();
    }
    /**
     * Creates user storage with ledgers.
     *
     * @param userName username
     * @param yearlyLedgers map of year to ledger
     */
    public UserStorage(String userName, Map<Integer, TransactionLedger> yearlyLedgers) {
    	this.setUserName(userName);
    	this.setYearlyLedgers(yearlyLedgers);
    }
    
    /** @return user name */
    public String getUserName() {
    	return this.userName;
    }
    /**
     * @param userName user name
     * @throws IllegalArgumentException if null, empty or contains '\\n'
     */
    public void setUserName(String userName) throws IllegalArgumentException {
    	if (userName == null) {
    		throw new IllegalArgumentException("[UserStorage.setUserName] ---> User name cannot be null.");
    	}
    	if (userName.isEmpty()) {
    		throw new IllegalArgumentException("[UserStorage.setUserName] ---> User name cannot be empty.");
    	}    	
    	if (userName.contains("\n")) {
    		throw new IllegalArgumentException("[UserStorage.setUserName] ---> User name cannot contain '\\n'.");
    	}
    	this.userName = userName;
    }

    
    /** @return yearly ledgers */
    public Map<Integer, TransactionLedger> getYearlyLedgers() {
    	Map<Integer, TransactionLedger> copyYearlyLedgers = new HashMap<Integer, TransactionLedger>();
    	for (Map.Entry<Integer, TransactionLedger> entry : yearlyLedgers.entrySet()) {
    	    Integer year = entry.getKey();
    	    TransactionLedger yearlyLedger = entry.getValue();
    	    copyYearlyLedgers.put(year, new TransactionLedger(yearlyLedger));
    	}
    	return copyYearlyLedgers;
    }
    /**
     * @param yearlyLedgers map of year to ledger
     * @throws IllegalArgumentException if null or not sorted by year
     */
    public void setYearlyLedgers(Map<Integer, TransactionLedger> yearlyLedgers) throws IllegalArgumentException {
    	if (yearlyLedgers == null) {
    	    throw new IllegalArgumentException("[UserStorage.setYearlyLedgers] ---> Yearly ledgers map cannot be null.");
    	}
    	Map<Integer, TransactionLedger> copyYearlyLedgers =  new HashMap<Integer, TransactionLedger>();
    	for (Map.Entry<Integer, TransactionLedger> entry : yearlyLedgers.entrySet()) {
    	    Integer year = entry.getKey();
    	    TransactionLedger yearlyLedger = entry.getValue();
    	    for (Transaction transaction : yearlyLedger.getTransactions()) {
    	    	if (transaction.getDate().getYear() != year) {
    	    		throw new IllegalArgumentException("[UserStorage.setYearlyLedgers] ---> Yearly ledgers not sorted."); 
    	    	}
    	    }
    	    copyYearlyLedgers.put(year, new TransactionLedger(yearlyLedger));
    	}
    	this.yearlyLedgers = copyYearlyLedgers;
    }
    
    
    /**
     * @param year year
     * @return ledger for year or if not found empty ledger
     */
    public TransactionLedger getLedgerByYear(int year) {
    	if (!yearlyLedgers.containsKey(year)) {
    		return new TransactionLedger();
    	}
    	else {
    		return new TransactionLedger(yearlyLedgers.get(year));
    	}
    }
    /**
     * @param year year
     * @return true if exist non empty ledger for year
     */
    public boolean hasLedgerForYear(int year) {
    	if (!yearlyLedgers.containsKey(year)) {
    		return false;
    	}
    	else {
    		return yearlyLedgers.get(year).getTransactions().size() > 0;
    	}
    }
    /**
     * Adds or replaces a ledger.
     *
     * @param ledger ledger to add
     * @throws IllegalArgumentException if null 
     */
    public void addLedger(TransactionLedger ledger) throws IllegalArgumentException {
    	if (ledger == null) {
    		throw new IllegalArgumentException("[UserStorage.addLedger] ---> Ledger cannot be null.");
    	}
    	for (Transaction transaction : ledger.getTransactions()) {
			int transactionYear = transaction.getDate().getYear();
			
			if (!this.yearlyLedgers.containsKey(transactionYear)) {
				this.yearlyLedgers.put(transactionYear, new TransactionLedger());
    		}
			
			TransactionLedger yearlyTransactionLedger = yearlyLedgers.get(transactionYear);
			try {
				yearlyTransactionLedger.addTransaction(transaction);
			}
			catch (Exception exception) {
	    		throw new IllegalArgumentException("[UserStorage.addLedger] ---> Problem adding transaction ---> " + exception); 
	    	}
			yearlyLedgers.put(transactionYear, yearlyTransactionLedger);
		}
    }
    /**
     * Removes ledger for a year.
     * 
     * @param year year
     * @throws IllegalArgumentException if not found
     */
    public void delLedger(int year) throws IllegalArgumentException {
    	if (!yearlyLedgers.containsKey(year)) {
    		throw new IllegalArgumentException("[UserStorage.delLedger] ---> Ledger not found to remove it."); 
    	}
    	yearlyLedgers.remove(year);
    }

    /**
     * Compares users by name.
     *
     * @param other other user
     * @return comparison result
     */
    @Override
    public int compareTo(UserStorage other) {
    	return this.userName.compareTo(other.userName);
    }
}
