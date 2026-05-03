// Import external dependencies
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a single financial transaction.
 */
final class Transaction implements Comparable<Transaction> {
	// Object fields 
	private LocalDate date;
	private String category;
	private static final Set<String> VALID_CATEGORIES = Set.of(
    // Income
    "Compensation", "Allowance", "Investments", "Other",
    // Expenses
    "Home", "Utilities", "Food", "Appearance", "Work", "Education", "Transportation", "Entertainment", "Professional Services" , "Other"
);
	private float amount;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	/**
     * Creates a copy transaction from other.
     *
     * @param other other transaction date
     * @throws IllegalArgumentException if other transaction is null
     */
	public Transaction(Transaction other) {
		if (other == null) {
			throw new IllegalArgumentException("[Transaction] ---> Other transaction cannot be null");
		}
	  	this.setDate(other.date);
    	this.setCategory(other.category);
    	this.setAmount(other.amount);
	}
    /**
     * Creates a transaction object.
     *
     * @param date transaction date
     * @param category transaction category
     * @param amount transaction amount
     * @throws IllegalArgumentException if any value is invalid
     */
    public Transaction(LocalDate date, String category, float amount) {
    	this.setDate(date);
    	this.setCategory(category);
    	this.setAmount(amount);
    }
    
    /**
     * Parses a CSV row into a Transaction object.
     *
     * @param text CSV formatted transaction string
     * @throws IllegalArgumentException if text is not in the correct format
     * @return Transaction object
     */
    public static Transaction createFromCSVText(String text) throws IllegalArgumentException {
    	text = text.replace("\uFEFF", "");
    	text = text.replace("\r", "");
    	String[] parts = text.split(",", -1);
    	
    	if (parts.length < 3) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Not enough data in the CSV text, expected 3 elements, received: " + parts.length + "."); 
    	}
    	if (parts.length > 3) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Too much data in the CSV text, expected 3 elements, received: " + parts.length + "."); 
    	}
    	
    	LocalDate date;
    	try {
    		date = LocalDate.parse(parts[0].trim(),DATE_FORMAT);
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Parsing problem for date parameter ---> " + exception); 
    	}
    	
      	String category;
    	try {
    		category = parts[1].trim();
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Parsing problem for category parameter ---> " + exception); 
    	}
    	
    	float amount;
    	try {
    		amount = Float.parseFloat(parts[2].trim());
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Parsing problem for amount parameter ---> " + exception); 
    	}
    	
    	Transaction transaction;
    	try {
    	    transaction = new Transaction(date, category, amount);
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Initialization problem ---> " + exception); 
    	}
    	
    	return transaction;
    	
    }
    /**
     * Converts this transaction to CSV format.
     *
     * @return CSV string
     */
    public String transformToCSVText() {
    	String text = "";	
    	text += date.format(DATE_FORMAT) + ",";
    	text += category + ",";
    	text += (int) amount; 
    	return text;
    }

    
    /** @return transaction date */
    public LocalDate getDate() {
    	return LocalDate.from(this.date);  // Making a copy for encapsulation
    }
    /**
     * @param date new date
     * @throws IllegalArgumentException if date is null
     */
    public void setDate(LocalDate date) throws IllegalArgumentException {
    	if (date == null) {
    		throw new IllegalArgumentException("[Transaction.setDate] ---> Date cannot be null.");
    	}
    	this.date = LocalDate.from(date);  // Making a copy for encapsulation
    }

    
    /** @return category */
    public String getCategory() {
    	return this.category;
    }
    /**
     * @param category new category
     * @throws IllegalArgumentException if null, empty or contains '\\n'
     */
	public void setCategory(String category) throws IllegalArgumentException {
    	if (category == null)
        	throw new IllegalArgumentException("[Transaction.setCategory] ---> Category cannot be null.");
    	if (category.isEmpty())
        	throw new IllegalArgumentException("[Transaction.setCategory] ---> Category cannot be empty.");
    	if (category.contains("\n"))
        	throw new IllegalArgumentException("[Transaction.setCategory] ---> Category cannot contain '\\n'.");
   		if (!VALID_CATEGORIES.contains(category))
        	throw new IllegalArgumentException(
                "[Transaction.setCategory] ---> Invalid category: \"" + category + "\". " +
                "Must be one of: " + VALID_CATEGORIES);
    	this.category = category;
	}
	
    /** @return amount */
    public float getAmount() {
    	return this.amount;
    }
    /**
     * @param amount new amount
     * @throws IllegalArgumentException if not valid
     */
    public void setAmount(float amount) throws IllegalArgumentException {
    	if (Float.isNaN(amount)) {
    		throw new IllegalArgumentException("[Transaction.setAmount] ---> Amount cannot be NaN.");
    	}
    	if (Float.isInfinite(amount)) {
    		throw new IllegalArgumentException("[Transaction.setAmount] ---> Amount cannot be infinite.");
    	}
    	this.amount = amount;
    }

    
    /** @return type */
    public String getType() {
    	return amount >= 0 ? "income" : "expense";
    }
    
    /**
     * Returns a hash code value for the transaction.
     *
     * @return a hash code value for this object
     */
    @Override
	public int hashCode() {
		return Objects.hash(amount, category, date);
	}
    /**
     * Compares this transaction to the specified object.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(date, other.date)                                    && 
				Objects.equals(category, other.category)                           && 
				Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount);
	}
    /**
     * Compares transactions by date.
     *
     * @param other another transaction
     * @return comparison result
     */
    @Override
    public int compareTo(Transaction other) {
    	return this.date.compareTo(other.date);
    }
}
