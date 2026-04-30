// Import external dependencies
import java.time.LocalDate;
import java.util.Objects;



/**
 * Represents a single financial transaction.
 */
final class Transaction implements Comparable<Transaction> {
	// Object fields 
	private LocalDate date;
	private String category;
	private String description;
	private float amount;
	private String type;
	
	
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
    	this.setDescription(other.description);
    	this.setAmount(other.amount);
    	this.setType(other.type);
	}
    /**
     * Creates a transaction object.
     *
     * @param date transaction date
     * @param category transaction category
     * @param description transaction description
     * @param amount transaction amount
     * @param type transaction type ("income" or "expense")
     * @throws IllegalArgumentException if any value is invalid
     */
    public Transaction(LocalDate date, String category, String description, float amount, String type) {
    	this.setDate(date);
    	this.setCategory(category);
    	this.setDescription(description);
    	this.setAmount(amount);
    	this.setType(type);
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
    	text = text.replace("  ", " ");
    	String[] parts = text.split(",", -1);
    	
    	if (parts.length < 5) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Not enough data in the CSV text, expected 5 elements, received: " + parts.length + "."); 
    	}
    	if (parts.length > 5) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Too much data in the CSV text, expected 5 elements, received: " + parts.length + "."); 
    	}
    	
    	LocalDate date;
    	try {
    		date = LocalDate.parse(parts[0].trim());
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
    	
    	String description;
    	try {
    		description = parts[2].trim();
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Parsing problem for description parameter ---> " + exception); 
    	}
    	
    	float amount;
    	try {
    		amount = Float.parseFloat(parts[3].trim());
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Parsing problem for amount parameter ---> " + exception); 
    	}
    	
    	String type;
    	try {
    		type = parts[4].trim();
    	}
    	catch (Exception exception) {
    		throw new IllegalArgumentException("[Transaction.createFromCSVText] ---> Parsing problem for type parameter ---> " + exception); 
    	}
    	
    	Transaction transaction;
    	try {
    	    transaction = new Transaction(date, category, description, amount, type);
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
    	text += date        + ",";
    	text += category    + ",";
    	text += description + ",";
    	text += amount      + ",";
    	text += type        + "\n";
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
    	if (category == null) {
    		throw new IllegalArgumentException("[Transaction.setCategory] ---> Category cannot be null.");
    	}
    	if (category.isEmpty()) {
    		throw new IllegalArgumentException("[Transaction.setCategory] ---> Category cannot be empty.");
    	}    	
    	if (category.contains("\n")) {
    		throw new IllegalArgumentException("[Transaction.setCategory] ---> Category cannot contain '\\n'.");
    	}
    	this.category = category;
    }

    
    /** @return description */
    public String getDescription() {
    	return this.description;
    }
    /**
     * @param description new description
     * @throws IllegalArgumentException if null, empty or contains '\\n'
     */
    public void setDescription(String description) {
    	if (description == null) {
    		throw new IllegalArgumentException("[Transaction.setDescription] ---> Description cannot be null.");
    	}
    	if (description.isEmpty()) {
    		throw new IllegalArgumentException("[Transaction.setDescription] ---> Description cannot be empty.");
    	}
    	if (description.contains("\n")) {
    		throw new IllegalArgumentException("[Transaction.setDescription] ---> Description cannot contain '\\n'.");
    	}
    	this.description = description;
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
    	if (amount < 0) {
    		throw new IllegalArgumentException("[Transaction.setAmount] ---> Amount cannot be negative.");
    	}
    	this.amount = amount;
    }

    
    /** @return type */
    public String getType() {
    	return this.type;
    }
    /**
     * @param type transaction type ("income" or "expense")
     * @throws IllegalArgumentException if invalid type
     */
    public void setType(String type) throws IllegalArgumentException {
    	if (!type.equals("income") && !type.equals("expense")) {
    		throw new IllegalArgumentException("[Transaction.setType] ---> Type cannot be '" + type + "', only \"income\" or \"expense\".");
    	}
    	this.type = type;
    }

    
    /**
     * Returns a hash code value for the transaction.
     *
     * @return a hash code value for this object
     */
    @Override
	public int hashCode() {
		return Objects.hash(amount, category, date, description, type);
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
				Objects.equals(description, other.description)                     && 
				Float.floatToIntBits(amount) == Float.floatToIntBits(other.amount) && 
				Objects.equals(type, other.type);
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