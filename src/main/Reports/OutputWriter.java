package Reports;

import java.io.IOException;

/**
 * @author Wesley Pilamunga
 * The OutputWriter interface defines methods for outputting
 * generated reports to different destinations.
 * 
 * Implementations may handle console output, file writing,
 * or other output formats as needed.
 */
interface OutputWriter {

    /**
     * Writes the given report content to the console.
     *
     * @param report the report content to be displayed
     */
    void writeToConsole(String report);

    /**
     * Writes the given report content to a file at the specified path.
     *
     * @param report the report content to be written
     * @param filePath the path of the file where the report will be saved
     * @param append if true, content will be appended to the file;
     *               if false, the file will be overwritten
     * @throws IOException if an I/O error occurs while writing to the file
     */
    void writeToFile(String report, String filePath, boolean append) throws IOException;
}