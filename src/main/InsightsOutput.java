import java.util.*;

/**
 * Handles the display and exportation of budget analysis results.
 */
public class InsightsOutput {

	/**
	 * * Prints results to the Eclipse console.
	 * 
	 * @author Hsaung
	 * @param results The analysis data to be displayed.
	 */
	public void displayToConsole(Map<String, Double> results) {
		// Logic to be added for Alpha build
		if (Objects.isNull(results) || results.isEmpty()) {
        System.out.println("No data available to display.");
        return;
        }
        results.forEach((category, value) -> {
			System.out.printf("Category: %-15s | Recommended Action: $%.2f%n", category, value);
		});
	}

	/**
	 * * Saves the results into a CSV file.
	 * 
	 * @author Hsaung
	 * @param results  The analysis data to be exported.
	 * @param filePath The location where the file should be saved.
	 */
	public void exportToCSV(Map<String, Double> results, String filePath) {
		// Logic to be added for Alpha build
	}
}
