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
         * @return the generated report as a string, or null if generation fails
    */
    private String generateReport(String userId, int year, String reportType) {

        UserStorage user = StorageService.loadUserStorage(userId);

        if(!user.hasLedgerForYear(year)) {
            return ("No ledger data found for user " + userId + " in year " + year);
        }
        TransactionLedger ledger = user.getLedgerByYear(year);
        
        ReportData data = new ReportData(ledger);

        return formatReport(data , reportType);

    }



    /**
     * Generates an annual report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     * @return the generated annual report as a string
     */
    @Override
    public String generateAnnualReport(String userId, int year) {

        return generateReport(userId, year,"annual");

        
    }

    /**
     * Generates a category report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     * @return the generated category report as a string
     */
    @Override
    public String generateCategoryReport(String userId, int year) {

        return generateReport(userId, year,"category");

        
    }

    /**
     * Generates a monthly summary report.
     *
     * @param userId the user identifier
     * @param year the report year
     * @return the generated monthly summary report as a string
     */
    @Override
    public String generateMonthlySummary(String userId, int year) {

        return generateReport(userId, year,"monthly");

    }

    /**
     * Formats report data into a readable string.
     *
     * @param data the report data
     * @param title the title of the report (e.g., "Annual", "Category", "Monthly")
     * @return formatted report text
     */

    public String formatReport(ReportData data, String title) {
        return new ReportFormatter().formatAsText(data, title);
    }
}