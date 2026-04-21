package Reports;
/**
 * Controller responsible for coordinating report generation and output delivery.
 * <p>
 * Acts as the entry point for report-related requests, delegating generation to
 * a {@link ReportService} and routing output to the appropriate {@link OutputWriter}
 * based on the requested output type.
 * </p>
 *
 * @author Juan Criollo
 */
public class ReportController {

    /**
     * Constructs a {@code ReportController} with the specified report service and output writer.
     *
     * @param reportService the service used to generate reports; must not be {@code null}
     * @param outputWriter  the default writer used to deliver report output; must not be {@code null}
     */
    public ReportController(ReportService reportService, OutputWriter outputWriter) {}

    /**
     * Handles a request to generate a report for the given user and year,
     * then writes the result using the output type specified.
     * <p>
     * The appropriate {@link OutputWriter} is selected via {@link #selectOutput(String)}.
     * </p>
     *
     * @param userId     the unique identifier of the user whose report is being generated
     * @param year       the calendar year for which the report is generated
     * @param outputType a string indicating the desired output destination
     *                   (e.g., {@code "console"} or {@code "file"})
     */
    public void handleGenerateReport(String userId, int year, String outputType) {}

    /**
     * Selects and returns the appropriate {@link OutputWriter} based on the given output type.
     *
     * @param outputType a string indicating the desired output destination
     *                   (e.g., {@code "console"} or {@code "file"})
     * @return the {@link OutputWriter} corresponding to the specified output type
     */
    public OutputWriter selectOutput(String outputType) {
        return null;
    }
}