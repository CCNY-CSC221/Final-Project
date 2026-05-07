//TODO add imports when Storage config is done
/**
 * Provides report generation services.
 * Implements the ReportGenerator interface.
 *
 * @author Nathaniel Kelly
 */
public class ReportService implements ReportGenerator {

    /**
     * Creates a ReportService.
     */
    public ReportService() {
    }

    /*
      *Helper method to load user data and generate a report based on the specified type.
         * @param userId the user identifier
         * @param year the report year
         * @param reportType the type of report to generate (e.g., "annual", "category", "monthly")
    */
    private void generateReport(String userId, int year, String reportType) {

        UserStorage user = StorageService.loadUserStorage(userId);

        if(!user.hasLedgerForYear(year)) {
            System.out.println("No ledger data found for user " + userId + " in year " + year);
            return;
        }
        TransactionLedger ledger = user.getLedgerByYear(year);
        
        ReportData data = new ReportData(ledger);

        System.out.println(formatReport(data));

    }



    /**
     * Generates an annual report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateAnnualReport(String userId, int year) {

        generateReport(userId, year,"annual");

        
    }

    /**
     * Generates a category report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateCategoryReport(String userId, int year) {

        generateReport(userId, year,"category");

        
    }

    /**
     * Generates a monthly summary report.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateMonthlySummary(String userId, int year) {

        generateReport(userId, year,"monthly");

    }

    /**
     * Formats report data into a readable string.
     *
     * @param data the report data
     * @return formatted report text
     */

    public String formatReport(ReportData data) {
        return new ReportFormatter().formatAsText(data);
    }
}