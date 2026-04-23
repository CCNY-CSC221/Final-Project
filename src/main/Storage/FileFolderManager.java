import java.io.IOException;

/**
 * Handles file/folder operations.
 */
public class FileFolderManager {
    
    /**
     * Creates a file.
     *
     * @param path file path
     * @throws IOException if file exist or path not valid
     */
    public static void createFile(String path) throws IOException {}
    
    /**
     * Deletes a file.
     *
     * @param path file path
     * @throws IOException if file does not exist
     */
    public static void deleteFile(String path) throws IOException{}

    /**
     * Checks if file exists.
     *
     * @param path file path
     * @return true if exists
     */
    public static boolean isFileExists(String path) throws IOException{}

    /**
     * Lists files in folder.
     *
     * @param path folder path
     * @return array of file names
     */
    public static String[] listFilesInFolder(String path) throws IOException{}
    
    /**
     
     * Reads file content.
     *
     * @param path file path
     * @return file content
     * @throws IOException if file not exist
     */
    public static String readFile(String path) throws IOException{}

    /**
     * Writes content to file.
     *
     * @param path file path
     * @param content text content
     * @throws IOException if file does not exist
     */
    public static void writeFile(String path, String content) throws IOException{}

    /**
     * Creates a folder.
     *
     * @param path folder path
     * @throws IOException if folder exist or path not valid
     */
    public static void createFolder(String path) throws IOException{}

    /**
     * Checks if folder exists.
     *
     * @param path folder path
     * @return true if exists
     */
    public static boolean isFolderExists(String path) {}

    /**
     * Lists folders in folder.
     *
     * @param path folder path
     * @return array of folder names
     */
    public static String[] listFoldersInFolder(String path) {}
     
    /**
     * Deletes a folder.
     *
     * @param path folder path
     * @throws IOException if folder does not exist
     */
    public static void deleteFolder(String path) throws IOException{}
}
