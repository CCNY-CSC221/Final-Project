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
	public Map<String, Float> analyzeDeficit(TransactionLedger ledger, List<String> targetCategories) {
		Map<String, Float> reductionSuggestions = new HashMap<>();
		if (ledger == null || targetCategories == null || targetCategories.isEmpty()) {
			return reductionSuggestions;
		}

		float totalIncome = 0;
		float totalExpense = 0;

		// MANUAL CALCULATION: We check the "type" string so positive/negative signs
		// don't break us
		for (Transaction t : ledger.getTransactions()) {
			if ("income".equals(t.getType())) {
				totalIncome += Math.abs(t.getAmount());
			} else if ("expense".equals(t.getType())) {
				totalExpense += Math.abs(t.getAmount());
			}
		}

		float totalDeficit = totalExpense - totalIncome;

		if (totalDeficit <= 0) {
			return reductionSuggestions;
		}

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

		// Filter for ONLY expenses and use absolute values
		for (Transaction t : ledger.getTransactions()) {
			if ("expense".equals(t.getType()) && !this.excludedCategories.contains(t.getCategory())) {
				float amount = Math.abs(t.getAmount());
				totalExpenses += amount;
				expenseTotals.put(t.getCategory(), expenseTotals.getOrDefault(t.getCategory(), 0f) + amount);
			}
		}

		if (totalExpenses == 0.0f) {
			return percentages;
		}

		for (Map.Entry<String, Float> entry : expenseTotals.entrySet()) {
			float percentage = (entry.getValue() / totalExpenses) * 100f;
			percentages.put(entry.getKey(), percentage);
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
			if ("income".equals(t.getType()))
				totalIncome += amount;
			if ("expense".equals(t.getType())) {
				totalExpense += amount;
				categoryMap.put(t.getCategory(), categoryMap.getOrDefault(t.getCategory(), 0f) + amount);
			}
		}

		float surplus = totalIncome - totalExpense;
		if (surplus <= 0)
			return analysis;

		float totalOfTargets = 0;
		for (String cat : targetCategories) {
			if (categoryMap.containsKey(cat)) {
				totalOfTargets += categoryMap.get(cat);
			}
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

		// TransactionLedger tl = (TransactionLedger) ledger;//Same logic as previous,
		// we cast to get access to category totals.
		Map<String, Float> categoryTotals = ledger.getCategoryTotals();

		List<String> targetCategories = new ArrayList<>();
		// We loop through transactions to make sure we ONLY analyze spending categories
		for (Transaction t : ledger.getTransactions()) {
			if ("expense".equals(t.getType())) {
				if (!targetCategories.contains(t.getCategory())) {
					targetCategories.add(t.getCategory());
				}
			}
		}

		Map<String, Float> surplus = analyzeSurplus(ledger, targetCategories);// run analyze surplus.
		Map<String, Float> deficit = analyzeDeficit(ledger, targetCategories);// run analyze defecit.

		if (surplus.isEmpty() == false) {// if there is a surplus print it.
			System.out.println("Surplus Analysis: ");
			insightsOutput.displayToConsole(surplus);

		} else if (deficit.isEmpty() == false) {// same as before.
			System.out.println("Deficit Analysis: ");
			insightsOutput.displayToConsole(deficit);

		} else {
			System.out.println("No surplus or deficit to analyze.");
		}
	}
}