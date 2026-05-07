//TODO add imports when Storage config is done
/**
 * Provides report generation services.
 * Implements the ReportGenerator interface.
 *
 * @author Nathaniel Kelly
 */
public class ReportService implements ReportGenerator {

    private StorageService storageService;

    /**
     * Creates a ReportService with default storage.
     */
    public ReportService() {
        this.storageService = new StorageService();
    }
       

    /**
     * Creates a ReportService with a specified storage service.
     *
     * @param storageService the storage service used for report data
     */
    public ReportService(StorageService storageService) {
        this.storageService = storageService;
      
    }

    /**
     * Generates an annual report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateAnnualReport(String userId, int year) {

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
     * Generates a category report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateCategoryReport(String userId, int year) {

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
     * Generates a monthly summary report.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateMonthlySummary(String userId, int year) {

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
     * Formats report data into a readable string.
     *
     * @param data the report data
     * @return formatted report text
     */

    public String formatReport(ReportData data) {
        return new ReportFormatter().formatAsText(data);
    }
}