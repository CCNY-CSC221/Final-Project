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
		if (ledger == null || targetCategories == null || targetCategories.isEmpty())
			return reductionSuggestions;

		float totalIncome = 0;
		float totalExpense = 0;

		for (Transaction t : ledger.getTransactions()) {
			if (isExpense(t)) {
				totalExpense += Math.abs(t.getAmount());
			} else if ("income".equalsIgnoreCase(t.getType())) {
				totalIncome += Math.abs(t.getAmount());
			}
		}

		float totalDeficit = totalExpense - totalIncome;
		if (totalDeficit <= 0)
			return reductionSuggestions;

		float cutAmountPerCategory = totalDeficit / targetCategories.size();
		DataValidator validator = new DataValidator();
		for (String category : targetCategories) {
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
		float totalIncome = 0;
		float totalExpense = 0;
		Map<String, Float> categoryMap = new HashMap<>();

		for (Transaction t : ledger.getTransactions()) {
			float amount = Math.abs(t.getAmount());
			if ("income".equalsIgnoreCase(t.getType())) {
				totalIncome += amount;
			} else if (isExpense(t)) {
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
		InsightsOutput insightsOutput = new InsightsOutput(); // how we access methods belonging to InsightsOutput.
		Map<String, Float> percentBreakdown = calculatePercentageBreakdown(ledger); // reference to @Wilson's method.
		insightsOutput.displayToConsole(percentBreakdown);// Send it to InsightsOutputs for display.

		List<String> targetCategories = new ArrayList<>();
		// We loop through transactions to make sure we ONLY analyze spending categories
		for (Transaction t : ledger.getTransactions()) {
			if (isExpense(t)) {
				if (!targetCategories.contains(t.getCategory())) {
					targetCategories.add(t.getCategory());
				}
			}
		}

		Map<String, Float> surplus = analyzeSurplus(ledger, targetCategories);// run analyze surplus.
		Map<String, Float> deficit = analyzeDeficit(ledger, targetCategories);// run analyze defecit.

		// THE FIX: Smart toggle ensures we display data even if there is a surplus
		if (deficit.isEmpty() == false) { // if there is a deficit print it.
			System.out.println("Deficit Analysis: ");
			insightsOutput.displayToConsole(deficit);
		} else if (surplus.isEmpty() == false) { // same as before, but for surplus.
			System.out.println("Surplus Analysis: ");
			insightsOutput.displayToConsole(surplus);
		} else {
			System.out.println("No surplus or deficit to analyze.");
		}
	}
}