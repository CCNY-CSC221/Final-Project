package Reports;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class stores the calculated financial data for reports.
 * * @author Henry Tran
 */

public class ReportData {

    private List<MonthlyTotal> monthlyTotals;
    private List<CategoryTotal> categoryTotals;
    private int netIncome;

    /**
     * 
     * Initalizing data strcutures with the constructor
     */

    public ReportData() {
        this.monthlyTotals = new ArrayList<>();
        this.categoryTotals = new ArrayList<>();
        this.netIncome = 0;
    }

    /**
     * 
     * @return A map of totals by month
     */

    public List<MonthlyTotal> getMonthlyTotals() {
        return monthlyTotals;
    }

    /**
     * 
     * @return A map of totals by category
     */

    public List<CategoryTotal> getCategoryTotals() {
        return categoryTotals;
    }

    /**
     * 
     * @return Obtaining the net income
     */

    public int getNetIncome() {
        return netIncome;
    }

    /**
     * 
     * Adds a transaction to the data
     * @param transaction The transaction to add
     */

    public void addTransaction(Object transaction) {
        // Writing 'Object' instead of 'Transaction' since we dont have the Storage Team's code yet
    }
}

/**
 * Including a helper class to store a month and its total
 * @author Henry Tran
 */

class MonthlyTotal {
    String monthName;
    int totalAmount;

    public MonthlyTotal(String month, int amount) {
        this.monthName = month;
        this.totalAmount = amount;
    }
}

/**
 * Including a helper class to store categories and its total
 * @author Henry Tran
 */

class CategoryTotal {
    String categoryName;
    int totalAmount;

    public CategoryTotal(String name, int amount) {
        this.categoryName = name;
        this.totalAmount = amount;
    }
}