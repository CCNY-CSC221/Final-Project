import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles the formatting and delivery of financial reports to the console and
 * CSV files.
 * 
 * @author Hsaung Eindra Soe
 */
public class InsightsOutput {

	/**
	 * Prints the financial analysis results directly to the console terminal.
	 * 
	 * @param reportData A map containing categories and their calculated financial
	 *                   totals.
	 */
	public void displayToConsole(Map<String, Float> reportData, boolean isPercentage) {
		System.out.println("\n*** HAMILTON HEIGHTS PFM: INSIGHTS REPORT ***");

		if (reportData == null || reportData.isEmpty()) {
			System.out.println("No data available to display.");
			return;
		}

		// Sets the header label based on the unit type
		String unitLabel = isPercentage ? "Value (%)" : "Amount ($)";
		System.out.printf("%-25s %12s%n", "Category", unitLabel);
		System.out.println("------------------------------------------");

		for (Map.Entry<String, Float> entry : reportData.entrySet()) {
			if (isPercentage) {
				// Wilson's Percentage Breakdown: Category 12.50%
				System.out.printf("%-25s %10.2f%%%n", entry.getKey(), entry.getValue());
			} else {
				// Clayton/Donnell Suggestions: Category $12.50
				// The $ is placed inside the printf for perfect vertical alignment
				System.out.printf("%-25s $%11.2f%n", entry.getKey(), entry.getValue());
			}
		}
		System.out.println("******************************************\n");
	}
	/**
	 * Saves the financial analysis results into a CSV file.
	 * 
	 * @param reportData The map of financial results to be saved.
	 * @param fileName   The target name for the file (e.g., "InsightReport.csv").
	 */
	public void exportToCSV(Map<String, Float> reportData, String fileName) {
		try (FileWriter writer = new FileWriter(fileName)) {
			// Requirement: First line must contain the header
			writer.append("Category,Amount\n");

			for (Map.Entry<String, Float> entry : reportData.entrySet()) {
				writer.append(entry.getKey()).append(",").append(String.valueOf(entry.getValue())).append("\n");
			}
			System.out.println("Analysis successfully exported to " + fileName);
		} catch (IOException e) {
			System.out.println("Error: Could not save the CSV file.");
		}
	}
}

/*
 * * TESTING LOG (Hsaung Eindra Soe): This code is used to verify the outputs on
 * April 29th using the following test block: * public static void main(String[]
 * args) { InsightsOutput tester = new InsightsOutput(); Map<String, Double>
 * testData = new HashMap<>(); testData.put("Food", -500.00f);
 * testData.put("Compensation", 4000.00f); testData.put("Entertainment",
 * -150.00)f; * tester.displayToConsole(testData); tester.exportToCSV(testData,
 * "test_report.csv"); } * RESULT: Console output displays correctly and CSV
 * file is generated in project root. DATA SOURCE: Integrated with
 * TransactionLedger.java (getCategoryTotals() method).
 */