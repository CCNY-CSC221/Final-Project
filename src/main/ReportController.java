import java.io.IOException;
//TODO add imports when Storage config is done
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

	private final ReportService reportService;
	private final OutputWriter outputWriter;

	// Default file path used when writing reports to disk
	private static final String DEFAULT_FILE_PATH = "report_output.txt";
	
    /**
     * Constructs a {@code ReportController} with the specified report service and output writer.
     *
     * @param reportService the service used to generate reports; must not be {@code null}
     * @param outputWriter  the default writer used to deliver report output; must not be {@code null}
     */
		
    public ReportController(ReportService reportService, OutputWriter outputWriter) {
    	this.reportService = reportService;
    	this.outputWriter = outputWriter;
    }

    /**
     * Handles a request to generate a report for the given user and year,
     * then writes the result using the output type specified.
     * <p>
     * The appropriate {@link OutputWriter} is selected via {@link #selectOutput(String)}.
     * </p>
     *
     * @param userId     the unique identifier of the user whose report is being generated
     * @param year       the calendar year for which the report is generated
	 * @param reportType a string indicating which report to generate 
	 *					 (e.g., {@code "annual"}, {@code "category"}, {@code "monthly"})	
     * @param outputType a string indicating the desired output destination
     *                   (e.g., {@code "console"} or {@code "file"})
     */
    
    public void handleGenerateReport(String userId, int year, String reportType, String outputType) {
    	// Capture console output by redirecting, or let the service print directly.
		// Since ReportService prints internally, we delegate and let selectOutput
		// handle any additional routing
		OutputWriter writer = selectOutput(outputType);
		
		switch ( reportType.toLowerCase()){
			case "annual":
				reportService.generateAnnualReport(userId, year);
				break;
			case "category":
				reportService.generateCategoryReport(userId, year);
				break;
			case "monthly":
				reportService.generateMonthlySummary(userId, year);
				break;
			default:
				System.out.println("Unkown report type: " + reportType);
		}
    }

	 /**
     * Handles a request to generate and write a pre-formatted report string.
     * <p>
     * Use this overload when the caller already holds a formatted report string
     * and simply needs it routed to the correct output destination.
     * </p>
     *
     * @param report     the pre-formatted report string to output
     * @param outputType a string indicating the desired output destination
     *                   (e.g., {@code "console"} or {@code "file"})
     * @param filePath   the file path to write to; only used when {@code outputType} is {@code "file"}
     * @param append     if {@code true}, content is appended to the file; if {@code false}, it overwrites
     */
    public void handleWriteReport(String report, String outputType, String filePath, boolean append) {
        OutputWriter writer = selectOutput(outputType);

        switch (outputType.toLowerCase()) {
            case "file":
                try {
                    String resolvedPath = (filePath != null && !filePath.isBlank())
                            ? filePath
                            : DEFAULT_FILE_PATH;
                    writer.writeToFile(report, resolvedPath, append);
                } catch (IOException e) {
                    System.err.println("Failed to write report to file: " + e.getMessage());
                }
                break;
            case "console":
            default:
                writer.writeToConsole(report);
                break;
        }
    }

    /**
     * Selects and returns the appropriate {@link OutputWriter} based on the given output type.
     *
     * @param outputType a string indicating the desired output destination
     *                   (e.g., {@code "console"} or {@code "file"})
     * @return the {@link OutputWriter} corresponding to the specified output type
     */
    
    public OutputWriter selectOutput(String outputType) {
        switch (outputType.toLowerCase()) {
            case "file":
                return new WriterService();
            case "console":
            default:
                return outputWriter; // default case falls back to console output
    	}    	
    }
}
