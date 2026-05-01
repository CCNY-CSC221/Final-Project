/**
 * This class formats the ReportData into readable text or CSV strings.
 * @author Henry Tran
 */

public class ReportFormatter {

    public ReportFormatter() {
    }

    /**
     * Formats data as a readable text summary.
     * @param reportData The data to format
     * @return A report of the data in text
     */

    public String formatAsText(ReportData reportData) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Financial Report ---\n");
        sb.append("Net Income: $").append(reportData.getNetIncome()).append("\n\n");

        sb.append("Totals by Category:\n");
        for (CategoryTotal ct : reportData.getCategoryTotals()) {
            sb.append("- ").append(ct.categoryName).append(": $").append(ct.totalAmount).append("\n");
        }

        sb.append("\nTotals by Month:\n");
        for (MonthlyTotal mt : reportData.getMonthlyTotals()) {
            sb.append("- ").append(mt.monthName).append(": $").append(mt.totalAmount).append("\n");
        }

        return sb.toString();
    }

    /**
     * Formats data into a CSV string
     * @param reportData The data to format.
     * @return A report of the data in CSV
     */

    public String formatAsCSV(ReportData reportData) {
        StringBuilder sb = new StringBuilder();
        sb.append("Type,Name,Amount\n");
        sb.append("Total,Net Income,").append(reportData.getNetIncome()).append("\n");

        for (CategoryTotal ct : reportData.getCategoryTotals()) {
            sb.append("Category,").append(ct.categoryName).append(",").append(ct.totalAmount).append("\n");
        }

        for (MonthlyTotal mt : reportData.getMonthlyTotals()) {
            sb.append("Month,").append(mt.monthName).append(",").append(mt.totalAmount).append("\n");
        }

        return sb.toString();
    }
}