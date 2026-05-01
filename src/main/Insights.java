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
	public Map<String, Float> analyzeSurplus(TransactionLedger ledger, List<String> targetCategories) {

		Map<String, Float> analysis = new HashMap<>();


		Float income = ledger.getTotalIncome(); //total income taken from ledger method.
		Float expense = ledger.getTotalExpense(); //total expenses taken from a ledger method.
		Map<String, Float> expenseCategories = ledger.getCategoryTotals(); //map of category to their expense.

		float surplus = income - expense; //Formula for surplus to be used in analyzing

		if(surplus <= 0){
			return analysis; //A surplus value of 0 or less means there is nothing to analyze. 
		}

		float totalPerCategory = 0; //THIS MAY BE REDUNDANT AND IF SO ILL FIX IT POST ALPHA-BUILD. the total amount spent on individual categories.

		for(String category: targetCategories){//Adding expenses
			if(expenseCategories.containsKey(category)){
				float amount = expenseCategories.get(category);
				if(amount < 0){
					totalPerCategory += Math.abs(amount);
				}
			}
		}

		if(totalPerCategory == 0){
			return analysis;
		}

		for(String category: targetCategories){
			if(expenseCategories.containsKey(category)){
				float amount = expenseCategories.get(category);
				if(amount < 0){
					float percentageWeight = Math.abs(amount) / totalPerCategory; //Find the amount that the category takes.
					float money = surplus * percentageWeight; //Multiply our surplus by that weight to determine how much is spent on the category.
					analysis.put(category, money);
				}
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
    InsightsOutput insightsOutput = new InsightsOutput(); //how we access methods belonging to InsightsOutput.
    Map<String, Float> percentBreakdown = calculatePercentageBreakdown(ledger); //reference to @Wilson's method.
    insightsOutput.displayToConsole(percentBreakdown);//Send it to InsightsOutputs for display.

    // TransactionLedger tl = (TransactionLedger) ledger;//Same logic as previous, we cast to get access to category totals.
    Map<String, Float> categoryTotals = ledger.getCategoryTotals();

    List<String> targetCategories = new ArrayList<>();//New ArrayList for categories we want to store.

    for (String category : categoryTotals.keySet()) {
        float amount = categoryTotals.get(category);//Retrieves expenses to be filtered.

        if (amount < 0) {//Filters out expenses
            targetCategories.add(category);//If its not an expense, it adds it to the ArrayList. // test? pls work
        }
    }

    Map<String, Float> surplus = analyzeSurplus(ledger, targetCategories);//run analyze surplus.
    Map<String, Float> deficit = analyzeDeficit(ledger, targetCategories);//run analyze defecit.

    if (surplus.isEmpty() == false) {//if there is a surplus print it.
        System.out.println("Surplus Analysis: ");
        insightsOutput.displayToConsole(surplus);

    } 
    else if (deficit.isEmpty() == false) {//same as before.
        System.out.println("Deficit Analysis: ");
        insightsOutput.displayToConsole(deficit);

    } 
    else {
        System.out.println("No surplus or deficit to analyze.");
    }
	}
}