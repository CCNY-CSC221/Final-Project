import java.time.LocalDate;

/**
 * Represents a single financial transaction.
 */
public class Transaction implements Comparable<Transaction> {

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
    public Transaction(LocalDate date, String category, String description, float amount, String type) {}

    /**
     * Parses a CSV row into a Transaction object.
     *
     * @param text CSV formatted transaction string
     * @return Transaction object
     */
    public static Transaction createFromCSVText(String text) {}

    /**
     * Converts this transaction to CSV format.
     *
     * @return CSV string
     */
    public String transformToCSVText() {}

    /** @return transaction date */
    public LocalDate getDate() {}

    /**
     * @param date new date
     * @throws IllegalArgumentException if date is null
     */
    public void setDate(LocalDate date) {}

    /** @return category */
    public String getCategory() {}

    /**
     * @param category new category
     * @throws IllegalArgumentException if null or empty
     */
    public void setCategory(String category) {}

    /** @return description */
    public String getDescription() {}

    /**
     * @param description new description
     * @throws IllegalArgumentException if null or empty
     */
    public void setDescription(String description) {}

    /** @return amount */
    public float getAmount() {}

    /**
     * @param amount new amount
     * @throws IllegalArgumentException if negative
     */
    public void setAmount(float amount) {}

    /** @return type */
    public String getType() {}

    /**
     * @param type transaction type ("income" or "expense")
     * @throws IllegalArgumentException if invalid type
     */
    public void setType(String type) {}

    /**
     * Compares transactions by date.
     *
     * @param other another transaction
     * @return comparison result
     */
    @Override
    public int compareTo(Transaction other) {}
}