import java.util.*;

/**
 * The Insights module performs financial analysis on validated transaction data
 * to provide the user with spending patterns, surpluses, and deficit warnings.
 */
public class Insights {

	private List<String> excludedCategories = new ArrayList<>();
	// Removed the private static final List<String> VALID_CATEGORIES - No longer
	// needed

	/**
	 * * Calculates required spending cuts for specific categories.
	 * 
	 * @author Clayton
	 * @param ledger           The financial data to be analyzed.
	 * @param targetCategories Categories to be reduced.
	 */
	public Map<String, Float> analyzeDeficit(Object ledger, List<String> targetCategories) {
		Map<String, Float> reductionSuggestions = new HashMap<>();
		DataValidator validator = new DataValidator(); // Line 1: Create the validator

		// Safety Check (Validation/Error)
		if (targetCategories == null || targetCategories.isEmpty()) {
			return reductionSuggestions;
		}

		/**
		 * FINAL INTEGRATION PLAN: Once TransactionLedger is moved to the default
		 * package, the lines below will replace the hardcoded placeholders: *
		 * TransactionLedger realLedger = (TransactionLedger) ledger; float totalIncome
		 * = realLedger.getTotalIncome(); float totalExpense =
		 * realLedger.getTotalExpense();
		 */

		// PLACEHOLDER VALUES for current development
		float totalIncome = 40000.0f;
		float totalExpense = 50000.0f;
		float totalDeficit = totalExpense - totalIncome;

		// Surplus Check
		if (totalDeficit <= 0) {
			return reductionSuggestions;
		}

		// Proportional Reduction Logic
		float cutAmountPerCategory = totalDeficit / targetCategories.size();

		for (String category : targetCategories) {
			// Line 2: Only add if the validation team's code says it's a real category
			if (validator.isValidCategory(category)) {
				reductionSuggestions.put(category, cutAmountPerCategory);
			}
		}

		return reductionSuggestions;
	}

	/**
	 * * Finds the percentage of total spending for each category.
	 * 
	 * @author Wilson
	 * @param ledger The financial data to be analyzed.
	 */
	public Map<String, Float> calculatePercentageBreakdown(TransactionLedger ledger) {
		Map<String, Float> percentages = new HashMap<>();
		Map<String, Float> categoryTotals = ledger.getCategoryTotals();

		float totalExpenses = 0.0f;

		// Find total expenses of non excluded categories.
		for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
			String category = entry.getKey();
			float totalAmount = entry.getValue();

			if (totalAmount < 0 && !this.excludedCategories.contains(category)) {
				totalExpenses += Math.abs(totalAmount);
			}
		}

		// Prevent dividing by 0.
		if (totalExpenses == 0.0f) {
			return percentages;
		}

		// Calculate percentage for each valid category
		for (Map.Entry<String, Float> entry : categoryTotals.entrySet()) {
			String category = entry.getKey();
			float totalAmount = entry.getValue();

			if (totalAmount < 0 && !this.excludedCategories.contains(category)) {
				float categoryTotal = Math.abs(totalAmount);
				float percentage = (categoryTotal / totalExpenses) * 100f;
				percentages.put(category, percentage);
			}
		}

		return percentages;
	}

	/**
	 * * Sets which categories to ignore in the analysis.
	 * 
	 * @author Wilson
	 * @param categories List of category names to skip.
	 */
	public void setExcludedCategories(List<String> categories) {
		DataValidator validator = new DataValidator();
		if (categories == null) {
			return;
		}

		excludedCategories.clear();

		for (String category : categories) {
			if (validator.isValidCategory(category)) {
				excludedCategories.add(category);
			} else {
				System.out.println(category + " is not a valid category to exclude");
			}
		}
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
