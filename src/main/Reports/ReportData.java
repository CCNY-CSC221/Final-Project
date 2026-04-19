package Reports;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 * This class stores the calculated financial data for reports.
 * * @author Henry Tran
 */

public class ReportData {

    private Map<String, Integer> monthlyTotals;
    private Map<String, Integer> categoryTotals;
    private int netIncome;

    /**
     * 
     * Initalizing data strcutures with the constructor
     */

    public ReportData() {
        this.monthlyTotals = new HashMap<>();
        this.categoryTotals = new HashMap<>();
        this.netIncome = 0;
    }

    /**
     * 
     * @return A map of totals by month
     */

    public Map<String, Integer> getMonthlyTotals() {
        return monthlyTotals;
    }

    /**
     * 
     * @return A map of totals by category
     */

    public Map<String, Integer> getCategoryTotals() {
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