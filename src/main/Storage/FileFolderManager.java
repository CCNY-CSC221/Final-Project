import java.io.IOException;
import java.io.File;
import java.io.file.Files;
import java.io.file.Paths;
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
        File file = new file(path);
        file.createNewFile();
    
    public static void deleteFile(String path) throws IOException{}
        File file = new file(path);
        file.delete();
    
    public static boolean isFileExists(String path) throws IOException{}
        File file = new File();
        return file.exists() && file.isFile();
    
    public static String[] listFilesInFolder(String path) throws IOException{}
        File folder = new File(path);
        String[] Contents = folder.list();
        return Contents;

    public static String readFile(String path) throws IOException{}
        return String(Files.readallBytes(Paths.get(path)));l

    public static void writeFile(String path, String content) throws IOException{}
        Files.write(Paths.get(path), content.getBytes())
            
    public static void createFolder(String path) throws IOException{}
        File folder = new File(path);
        folder.mldir();

    public static boolean isFolderExists(String path) {}
        File folder = new File(path);
        return file.exists() && file.isDirectory();

    public static String[] listFoldersInFolder(String path) {}
        File folder = new File(path);
        String[] Contents = folder.list();
        
        String[] folders = new String(Contents.length())
        int i = 0
        for (File f : Contents) {
            if (f.isDirectory()) {
                folders[i++] = f.getname();
            }    
        }   

    public static void deleteFolder(String path) throws IOException{}
        File file = new file(path);
        file.delete();

}
