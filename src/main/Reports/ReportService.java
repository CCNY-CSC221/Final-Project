package Reports;
/**
 * Provides report generation services.
 * Implements the ReportGenerator interface.
 *
 * @author Nathaniel Kelly
 */
public class ReportService implements ReportGenerator {

    private int storageService;

    /**
     * Creates a ReportService with default storage.
     */
    public ReportService() {
    }

    /**
     * Creates a ReportService with a specified storage service.
     *
     * @param storageService the storage service used for report data // TODO: Change to StorageService type when implemented
     */
    public ReportService(int storageService) {
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
    }

    /**
     * Generates a category report for a user.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateCategoryReport(String userId, int year) {
    }

    /**
     * Generates a monthly summary report.
     *
     * @param userId the user identifier
     * @param year the report year
     */
    @Override
    public void generateMonthlySummary(String userId, int year) {
    }

    /**
     * Formats report data into a readable string.
     *
     * @param data the report data
     * @return formatted report text
     */
    public String formatReport(ReportData data) {
        return "";
    }
}