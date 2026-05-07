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
     * @param reportTitle The title of the report (e.g., "Annual", "Monthly", "Category")
     * @return A report of the data in text
     */

    public String formatAsText(ReportData reportData, String reportTitle) {
        // Null safety check to prevent program crashes
        if (reportData == null) {
            return "Error: Report data is null.";
        }

        StringBuilder sb = new StringBuilder();
        
        // Dynamic title implementation
        sb.append("--- ").append(reportTitle).append(" Report ---\n");
        sb.append("Net Income: $").append(reportData.getNetIncome()).append("\n\n");

        sb.append("Totals by Category:\n");
        if (reportData.getCategoryTotals() != null) {
            for (CategoryTotal ct : reportData.getCategoryTotals()) {
                sb.append("- ").append(ct.categoryName).append(": $").append(ct.totalAmount).append("\n");
            }
        }

        sb.append("\nTotals by Month:\n");
        if (reportData.getMonthlyTotals() != null) {
            for (MonthlyTotal mt : reportData.getMonthlyTotals()) {
                sb.append("- ").append(mt.monthName).append(": $").append(mt.totalAmount).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Formats data into a CSV string
     * @param reportData The data to format.
     * @return A report of the data in CSV
     */

    public String formatAsCSV(ReportData reportData) {
        // Null safety check
        if (reportData == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Type,Name,Amount\n");
        sb.append("Total,Net Income,").append(reportData.getNetIncome()).append("\n");

        if (reportData.getCategoryTotals() != null) {
            for (CategoryTotal ct : reportData.getCategoryTotals()) {
                sb.append("Category,").append(ct.categoryName).append(",").append(ct.totalAmount).append("\n");
            }
        }

        if (reportData.getMonthlyTotals() != null) {
            for (MonthlyTotal mt : reportData.getMonthlyTotals()) {
                sb.append("Month,").append(mt.monthName).append(",").append(mt.totalAmount).append("\n");
            }
        }

        return sb.toString();
    }
}