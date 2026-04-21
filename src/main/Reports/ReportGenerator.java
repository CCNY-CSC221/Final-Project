package Reports;

/**
 * @author Wesley Pilamunga
 * The ReportGenerator interface defines methods for generating
 * different types of financial reports for a user.
 * 
 * Implementations of this interface should provide logic to
 * analyze user data and produce annual, category-based,
 * and monthly summary reports.
 */
interface ReportGenerator {

    /**
     * Generates an annual financial report for a specific user and year.
     *
     * @param userId the unique identifier of the user
     * @param year the year for which the report is generated
     */
    void generateAnnualReport(String userId, int year);

    /**
     * Generates a category-based financial report for a specific user and year.
     * This report typically groups income and expenses by category.
     *
     * @param userId the unique identifier of the user
     * @param year the year for which the report is generated
     */
    void generateCategoryReport(String userId, int year);

    /**
     * Generates a monthly summary report for a specific user and year.
     * This report compares income and expenses on a month-to-month basis.
     *
     * @param userId the unique identifier of the user
     * @param year the year for which the report is generated
     */
    void generateMonthlySummary(String userId, int year);
}