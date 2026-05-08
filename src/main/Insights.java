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
	 * PRIVATE HELPER: Unified rule for identifying an expense. Checks for negative
	 * amounts OR the explicit "expense" label.
	 */
	private boolean isExpense(Transaction t) {
		float amount = t.getAmount();
		String type = t.getType();
		return (amount < 0 || "expense".equalsIgnoreCase(type));
	}

	/**
	 * * Calculates required spending cuts for specific categories.
	 * 
	 * @author Clayton
	 * @param ledger           The financial data to be analyzed.
	 * @param targetCategories Categories to be reduced.
	 */
	public Map<String, Float> analyzeDeficit(TransactionLedger ledger, List<String> targetCategories) {
		Map<String, Float> reductionSuggestions = new HashMap<>();

		// Basic safety check for input
		if (ledger == null || targetCategories == null || targetCategories.isEmpty()) {
			return reductionSuggestions;
		}

		float totalIncome = 0;
		float totalExpense = 0;
		Map<String, Float> categoryTotals = ledger.getCategoryTotals();

		// Calculate total income and expenses
		for (Transaction t : ledger.getTransactions()) {
			if (isExpense(t)) {
				totalExpense += Math.abs(t.getAmount());
			} else if ("income".equalsIgnoreCase(t.getType())) {
				totalIncome += Math.abs(t.getAmount());
			}
		}

		// Calculate the gap between spending and earning
		float totalDeficit = totalExpense - totalIncome;

		// If there is no debt (Surplus), show saving advice instead of an empty screen
		if (totalDeficit <= 0) {
			return analyzeSurplus(ledger, targetCategories);
		}

		DataValidator validator = new DataValidator();

		// Suggest cuts based on how much was actually spent in each category
		for (String category : targetCategories) {
			if (validator.isValidCategory(category)) {
				float categorySpent = categoryTotals.getOrDefault(category, 0f);

				// Proportional math: more spending = higher suggested cut
				float proportionalCut = (Math.abs(categorySpent) / totalExpense) * totalDeficit;

				reductionSuggestions.put(category, proportionalCut);
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
		float totalExpenses = 0.0f;
		Map<String, Float> expenseTotals = new HashMap<>();

		for (Transaction t : ledger.getTransactions()) {
			// Using isExpense to ensure logic matches analyzeDeficit
			if (isExpense(t) && !this.excludedCategories.contains(t.getCategory())) {
				float absAmount = Math.abs(t.getAmount());
				totalExpenses += absAmount;
				expenseTotals.put(t.getCategory(), expenseTotals.getOrDefault(t.getCategory(), 0f) + absAmount);
			}
		}

		if (totalExpenses == 0.0f)
			return percentages;

		for (Map.Entry<String, Float> entry : expenseTotals.entrySet()) {
			percentages.put(entry.getKey(), (entry.getValue() / totalExpenses) * 100f);
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
			System.out.println("No categories listed to exclude.");
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
	public Map<String, Float> analyzeSurplus(TransactionLedger ledger, List<String> targetCategories) {
		Map<String, Float> analysis = new HashMap<>();
		if (ledger == null || targetCategories == null || targetCategories.isEmpty()) {
    		return analysis;
		}
		float totalIncome = 0;
		float totalExpense = 0;
		Map<String, Float> categoryMap = new HashMap<>();

		for (Transaction t : ledger.getTransactions()) {
			float amount = Math.abs(t.getAmount());
			if ("income".equalsIgnoreCase(t.getType())) {
				totalIncome += amount;
			} else if (isExpense(t) && !excludedCategories.contains(t.getCategory())) {
				totalExpense += amount;
				categoryMap.put(t.getCategory(), categoryMap.getOrDefault(t.getCategory(), 0f) + amount);
			}
		}

		float surplus = totalIncome - totalExpense;
		if (surplus <= 0)
			return analysis;

		float totalOfTargets = 0;
		for (String cat : targetCategories) {
			if (categoryMap.containsKey(cat))
				totalOfTargets += categoryMap.get(cat);
		}

		if (totalOfTargets == 0)
			return analysis;

		for (String cat : targetCategories) {
			if (categoryMap.containsKey(cat)) {
				float weight = categoryMap.get(cat) / totalOfTargets;
				analysis.put(cat, surplus * weight);
			}
		}
		return analysis;
	}

	/**
	 * * Runs the full analysis and generates the report.
	 * 
	 * @author Donnell
	 * @param ledger The financial data to be analyzed.
	 */
	public void generateInsightReport(TransactionLedger ledger) {
		if (ledger == null) {
    		System.out.println("No ledger data available for insights.");
    		return;
		}
		InsightsOutput insightsOutput = new InsightsOutput();

		// WILSON'S SECTION: Percentage Breakdown
		Map<String, Float> percentBreakdown = calculatePercentageBreakdown(ledger);
		System.out.println("Percentage of Total Spending by Category:");
		// Explicitly pass 'true' to ensure this section shows % signs
		insightsOutput.displayToConsole(percentBreakdown, true);

		List<String> targetCategories = new ArrayList<>();
		for (Transaction t : ledger.getTransactions()) {
			if (isExpense(t) && !excludedCategories.contains(t.getCategory())) {
				if (!targetCategories.contains(t.getCategory())) {
					targetCategories.add(t.getCategory());
				}
			}
		}

		// CLAYTON/DONNELL SECTION: Surplus and Deficit
		Map<String, Float> surplus = analyzeSurplus(ledger, targetCategories);
		Map<String, Float> deficit = analyzeDeficit(ledger, targetCategories);

		if (!deficit.isEmpty()) {
			System.out.println("Deficit Analysis (Suggested Cuts):");
			// Pass 'false' to display dollar signs ($)
			insightsOutput.displayToConsole(deficit, false);
		} else if (!surplus.isEmpty()) {
			System.out.println("Surplus Analysis (Suggested Allocation):");
			// Pass 'false' to display dollar signs ($)
			insightsOutput.displayToConsole(surplus, false);
		} else {
			System.out.println("No surplus or deficit to analyze.");
		}
	}
}