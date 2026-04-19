package Reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Wesley Pilamunga
 * The WriterService Class writes reports to the console or to a file.
 * 
 * This service provides concrete implementation for OutputWriter,
 * allowing reports to be displayed in the console or saved to disk.
 */
public class WriterService implements OutputWriter {

    /**
     * Writes the given report content to the console.
     *
     * @param report the report content to be displayed
     */
    @Override
    public void writeToConsole(String report) {
        if (report == null || report.isBlank()) {
            return;
        }
        System.out.println(report);
    }

    /**
     * Writes the given report content to a file at the specified path.
     *
     * @param report the report content to be written
     * @param filePath the path of the file where the report will be saved
     * @param append if true, content will be appended to the file;
     *               if false, the file will be overwritten
     * @throws IOException if an I/O error occurs while writing to the file
     */
    @Override
    public void writeToFile(String report, String filePath, boolean append) {
        if (report == null || report.isBlank() || filePath == null || filePath.isBlank()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(report);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report to file: " + filePath, e);
        }
    }
}