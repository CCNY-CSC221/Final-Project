import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
//TODO add Transaction import when Storage config is done

/**
 * This class stores the calculated financial data for reports.
 * It uses helper classes and ArrayLists to group data.
 * @author Henry Tran
 */
public class ReportData {

    private List<MonthlyTotal> monthlyTotals;
    private List<CategoryTotal> categoryTotals;
    private float netIncome;

    /**
     * Initializes data structures with the constructor.
     */

    public ReportData() {
        this.monthlyTotals = new ArrayList<>();
        this.categoryTotals = new ArrayList<>();
        this.netIncome = 0;
    }

    public ReportData(TransactionLedger ledger) {
        this();
        for (Transaction transaction : ledger.getTransactions()) {
            addTransaction(transaction);
        }
    }

    /**
     * @return A list of totals grouped by month.
     */

    public List<MonthlyTotal> getMonthlyTotals() {
        return monthlyTotals;
    }

    /**
     * @return A list of totals grouped by category.
     */

    public List<CategoryTotal> getCategoryTotals() {
        return categoryTotals;
    }

    /**
     * @return The current calculated net income.
     */

    public float getNetIncome() {
        return netIncome;
    }

    /**
     * Adds a transaction and updates monthly, category, and net totals.
     * @param transaction The transaction object from the Storage Team.
     */

    public void addTransaction(Transaction transaction) {
        String category = transaction.getCategory();

        // Extract month name from LocalDate

        String month = transaction.getDate().getMonth().toString();
        float amount = transaction.getAmount();

        // 1 - Update Category Totals

        boolean categoryFound = false;
        for (CategoryTotal ct : categoryTotals) {
            if (ct.categoryName.equalsIgnoreCase(category)) {
                ct.totalAmount += amount;
                categoryFound = true;
                break;
            }
        }
        if (!categoryFound) {
            categoryTotals.add(new CategoryTotal(category, amount));
        }

        // 2 - Update Monthly Totals

        boolean monthFound = false;
        for (MonthlyTotal mt : monthlyTotals) {
            if (mt.monthName.equalsIgnoreCase(month)) {
                mt.totalAmount += amount;
                monthFound = true;
                break;
            }
        }
        if (!monthFound) {
            monthlyTotals.add(new MonthlyTotal(month, amount));
        }

        // 3 - Update net incoem (Income vs Expense)

        if (transaction.getType().equalsIgnoreCase("income")) {
            this.netIncome += amount;
        } else {
            this.netIncome -= amount;
        }
    }
}

/**
 * Helper class to store a month and its total amount.
 * @author Henry Tran
 */

class MonthlyTotal {
    String monthName;
    float totalAmount;

    public MonthlyTotal(String month, float amount) {
        this.monthName = month;
        this.totalAmount = amount;
    }
}

/**
 * Helper class to store a category name and its total amount.
 * @author Henry Tran
 */

class CategoryTotal {
    String categoryName;
    float totalAmount;

    public CategoryTotal(String name, float amount) {
        this.categoryName = name;
        this.totalAmount = amount;
    }
}