// Import external dependencies
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



/**
 * Represents a ledger of transactions.
 */
final class TransactionLedger implements Comparable<TransactionLedger> {
	// Object fields 
	private ArrayList<Transaction> transactions;
	
	
	/**
     * Creates ledger without transactions.
     */
	public TransactionLedger() {
		this.setTransactions(new ArrayList<Transaction>());
	}
	/**
     * Creates a copy ledger from other.
     *
     * @param other other ledger
     * @throws IllegalArgumentException if other transaction is null
     */
	public TransactionLedger(TransactionLedger other) {
		this.setTransactions(other.getTransactions());
	}
    /**
     * Creates ledger from list of transactions.
     *
     * @param transactions list of transactions
     */
    public TransactionLedger(ArrayList<Transaction> transactions) {
    	this.setTransactions(transactions);
    }
    
    
    /**
     * Parses CSV text into a ledger of transactions.
     *
     * @param text CSV text
     * @throws IllegalArgumentException if text is not in the correct format
     * @return TransactionLedger object
     */
    public static TransactionLedger createFromCSVText(String text) {
    	text = text.replace("\uFEFF", "");
    	String[] linesOfText = text.split("\n");
    	
    	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    	for (int lineIndex=0; lineIndex<linesOfText.length; lineIndex ++) {
    		String lineOfText = linesOfText[lineIndex];
    		
    		if (lineOfText.isEmpty()) {
    			continue;
    		}
    		if (lineOfText.trim().equals("Date,Category,Amount")) {
    		    continue;
    		}
    		
    		Transaction transaction;
    		try {
    			transaction = Transaction.createFromCSVText(lineOfText);
    		}
    		catch (Exception exception) {
    			throw new IllegalArgumentException("[TransactionLedger.createFromCSVText] ---> Parsing problem in line #" + (lineIndex+1) + " ---> " + exception); 
    		}
    		
    		transactions.add(transaction);
    	}
    	
    	TransactionLedger transactionLedger;
		try {
			transactionLedger = new TransactionLedger(transactions);
		}
		catch (Exception exception) {
			throw new IllegalArgumentException("[TransactionLedger.createFromCSVText] ---> Initialization problem ---> " + exception); 
		}
		
		return transactionLedger;
    }
    /**
     * Converts ledger to CSV text.
     *
     * @return CSV string
     */
    public String transformToCSVText() {
    	String text = "Date,Category,Amount\n";
    	for (Transaction transaction : this.transactions) {
    		text += transaction.transformToCSVText() + "\n";
    	}
		return text;
    }

    
    /** @return transaction list */
    public ArrayList<Transaction> getTransactions() {
    	ArrayList<Transaction> copyTransactions = new ArrayList<Transaction>();
    	for (Transaction transaction : this.transactions) {
    		Transaction copyTransaction = new Transaction(transaction);
    		copyTransactions.add(copyTransaction);
    	}
    	return copyTransactions;
    }
    /**
     * @param transactions new transaction list
     */
    public void setTransactions(ArrayList<Transaction> transactions) {
    	ArrayList<Transaction> copyTransactions = new ArrayList<Transaction>();
    	for (Transaction transaction : transactions) {
    		if (transaction == null) {
    			continue;
    		}
    		if (copyTransactions.contains(transaction)) {  
    			continue;
    		}
    		Transaction copyTransaction = new Transaction(transaction);
    		copyTransactions.add(copyTransaction);
    	}
    	this.transactions = copyTransactions;
    }

    
    /**
     * Adds a transaction.
     *
     * @param transaction transaction to add
     * @throws IllegalArgumentException if null 
     */
    public void addTransaction(Transaction transaction) {
    	if (transaction == null) {
    		throw new IllegalArgumentException("[TransactionLedger.addTransaction] ---> Transaction cannot be null.");
    	}
    	if (this.transactions.contains(transaction)) {
    		return;
    	}
    	transactions.add(new Transaction(transaction));
    }
    /**
     * Removes a transaction.
     *
     * @param transaction transaction to remove
     * @throws IllegalArgumentException if not found
     */
    public void delTransaction(Transaction transaction) {
    	boolean isFound = transactions.remove(transaction);
    	if (!isFound) {
    		throw new IllegalArgumentException("[TransactionLedger.delTransaction] ---> Transaction not found to remove it."); 
    	}
    }

    
    /** @return total income */
    public float getTotalIncome() {
    	float totalIncome = 0;
    	for (Transaction transaction : this.transactions) {
            if ("income".equals(transaction.getType())) {
                totalIncome += transaction.getAmount();
            }
        }
        return totalIncome;
    }
    /** @return total expense */
    public float getTotalExpense() {    	
    	float totalExpense = 0f;
		for (Transaction transaction : this.transactions) {
	        if (transaction.getAmount() < 0) {
	            totalExpense += Math.abs(transaction.getAmount());
	        }
	    }
	    return totalExpense;
    }
    /**
     * Calculate net income for the proposed month.
     * 
     * @param month month (1–12)
     * @throws IllegalArgumentException if month beyond range
     * @return net income for month
     */
    public float getMonthlyNetIncome(int month) throws IllegalArgumentException {
    	if ((month < 1) || (month > 12)) {
    		throw new IllegalArgumentException("[TransactionLedger.getMonthlyNetIncome] ---> Month beyond range(1–12)."); 
    	}
    	float netIncome = 0;
		for (Transaction transaction : this.transactions) {
			if (transaction.getDate().getMonthValue() != month) {
				continue;
			}		
				netIncome += transaction.getAmount();
		}
		
		return netIncome;
    }
    /** 
     * Creates a map of totals for each category.
     * 
     * @return category totals map 
     */
    public Map<String, Float> getCategoryTotals() {
    	Map<String, Float> categoryTotals = new HashMap<String, Float>();
    	for (Transaction transaction : this.transactions) {
    		String category = transaction.getCategory();
    		float amount = transaction.getAmount();
    		String type = transaction.getType();
    		
    		if (!categoryTotals.containsKey(category)) {
    			categoryTotals.put(category, 0.0f);
    		}
    		
    		if ("income".equals(type)) {
    			categoryTotals.put(category, categoryTotals.get(category) + amount);
    		}
    		if ("expense".equals(type)) {
    			categoryTotals.put(category, categoryTotals.get(category) - amount);
    		}
    	}
    	return  categoryTotals;
    }

    
    /**
     * Returns a hash code value for the ledger.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }
    /**
     * Compares this ledger to the specified object.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TransactionLedger other = (TransactionLedger) obj;
        return Objects.equals(transactions, other.transactions);
    }
    /**
     * Compares ledgers by size.
     *
     * @param other other ledger
     * @return comparison result
     */
    @Override
    public int compareTo(TransactionLedger other) {
        return Integer.compare(this.transactions.size(), other.transactions.size());
    }
}
