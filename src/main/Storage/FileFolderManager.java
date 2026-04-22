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
    public static void createFile(String path) {}
    
    /**
     * Deletes a file.
     *
     * @param path file path
     * @throws IOException if file does not exist
     */
    public static void deleteFile(String path) {}

    /**
     * Checks if file exists.
     *
     * @param path file path
     * @return true if exists
     */
    public static boolean isFileExists(String path) {}

    /**
     * Lists files in folder.
     *
     * @param path folder path
     * @return array of file names
     */
    public static String[] listFilesInFolder(String path) {}
    
    /**
     
     * Reads file content.
     *
     * @param path file path
     * @return file content
     * @throws IOException if file not exist
     */
    public static String readFile(String path) {}

    /**
     * Writes content to file.
     *
     * @param path file path
     * @param content text content
     * @throws IOException if file does not exist
     */
    public static void writeFile(String path, String content) {}

    /**
     * Creates a folder.
     *
     * @param path folder path
     * @throws IOException if folder exist or path not valid
     */
    public static void createFolder(String path) {}

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
    public static void deleteFolder(String path) {}
}