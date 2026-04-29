import java.util.*;

/**
 * The Insights module performs financial analysis on validated transaction data
 * to provide the user with spending patterns, surpluses, and deficit warnings.
 */
public class Insights {
	
	private List<String> excludedCategories = new ArrayList<>();
	private static final List<String> VALID_CATEGORIES = Arrays.asList(
			"Compensation", "Allowance", "Investments", "Other", 
			"Home", "Utilities", "Food", "Appearance", "Work", "Education", "Transportation", "Entertainment", "Professional Services");
	/**
	 * * Calculates required spending cuts for specific categories.
	 * 
	 * @author Clayton
	 * @param ledger           The financial data to be analyzed.
	 * @param targetCategories Categories to be reduced.
	 */
	public Map<String, Double> analyzeDeficit(Object ledger, List<String> targetCategories) {
		Map<String, Double> reductionSuggestions = new HashMap<>();

		// Safety Check (Validation/Error)
		if (targetCategories == null || targetCategories.isEmpty()) {
			return reductionSuggestions;
		}

		/**
		 * Integration Bridge Once Storage moves to default package, uncomment the lines
		 * below: TransactionLedger ledger = (TransactionLedger) ledgerPlaceholder;
		 * double totalIncome = ledger.getTotalIncome(); double totalExpense =
		 * ledger.getTotalExpense();
		 */

		// PLACEHOLDER VALUES for current development
		double totalIncome = 40000.0;
		double totalExpense = 50000.0;
		double totalDeficit = totalExpense - totalIncome;

		// Surplus Check (If income >= expenses, no cuts needed)
		if (totalDeficit <= 0) {
			return reductionSuggestions;
		}

		// Proportional Reduction Logic
		// We divide the total deficit by the number of categories the user is willing
		// to cut.
		double cutAmountPerCategory = totalDeficit / targetCategories.size();

		for (String category : targetCategories) {
			reductionSuggestions.put(category, cutAmountPerCategory);
		}

		return reductionSuggestions;

	}

	/**
	 * * Finds the percentage of total spending for each category.
	 * 
	 * @author Wilson
	 * @param ledger The financial data to be analyzed.
	 */
	public Map<String, Double> calculatePercentageBreakdown(Object ledger) {
		Map<String, Double> percentages = new HashMap<>();
		Map<String, Float> categoryTotals = ledger.getCategoryTotals();
		
		double totalExpenses = 0;
		
		for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
			String category = entry.getKey();
			float totalAmount = entry.getValue();
			
			if (totalAmount < 0 && !this.excludedCategories.contains(category)) {
				totalExpenses += Math.abs((double) totalAmount);
			}
		}
		
		if (totalExpenses == 0) {
			return percentages;
		}
		
	}

	/**
	 * * Sets which categories to ignore in the analysis.
	 * 
	 * @author Wilson
	 * @param categories List of category names to skip.
	 */
	public void setExcludedCategories(List<String> categories) {
		if (categories == null) {
			return;
		}
		
		excludedCategories.clear();
		
		for (String category : categories) {
			if (isValidCategory(category)) {
				excludedCategories.add(category);
			}
			else {
				System.err.println(category + " is not a valid category to exclude");
			}
		}
	}

	/**
	 * * Helper method to see if a category is valid.
	 * 
	 * @author Wilson
	 * @param category
	 */
	private boolean isValidCategory(String category) {
		for (String valid: VALID_CATEGORIES) {
			if (category.equalsIgnoreCase(valid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * * Finds extra money and suggests where to spend it.
	 * 
	 * @author Donnell
	 * @param ledger           The financial data to be analyzed.
	 * @param targetCategories Categories to potentially increase.
	 */
	public Map<String, Double> analyzeSurplus(Object ledger, List<String> targetCategories) {
		return null;
	}

	/**
	 * * Runs the full analysis and generates the report.
	 * 
	 * @author Donnell
	 * @param ledger The financial data to be analyzed.
	 */
	public void generateInsightReport(Object ledger) {
	}
}
