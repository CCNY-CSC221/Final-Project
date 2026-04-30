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
    public static void createFile(String path) throws IOException {
        File file = new file(path);
        file.createNewFile();
    }
    public static void deleteFile(String path) throws IOException{
        File file = new file(path);
        file.delete();
    }
    public static boolean isFileExists(String path) throws IOException{
        File file = new File(path);
        return file.exists() && file.isFile();
    }
    
    public static String[] listFilesInFolder(String path) throws IOException{
        File folder = new File(path);
        String[] Contents = folder.list();
        return Contents;
    }
    public static String readFile(String path) throws IOException{
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    
    public static void writeFile(String path, String content) throws IOException{
        Files.write(Paths.get(path), content.getBytes());
    }
    
    public static void createFolder(String path) throws IOException{
        File folder = new File(path);
        folder.mkdir();
    }
    public static boolean isFolderExists(String path) {
        File folder = new File(path);
        return folder.exists() && file.isDirectory();
    }
    public static String[] listFoldersInFolder(String path) {
        File folder = new File(path);
        String[] contents = folder.list();
        
        String[] folders = new String(Contents.length());
        int i = 0;
        for (String name : contents) {
            File f = new file(path,name);
            if (f.isDirectory()) {
                folders[i++] = f.getname();
            }    
        }
        return folders; 
    }
    public static void deleteFolder(String path) throws IOException{
        File file = new file(path);
        file.delete();
    }
}
