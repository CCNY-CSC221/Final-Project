// @author Andre 
// Import external dependencies
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;



public class FileFolderManager {
	/**
     * Creates a file.
     * 
     * @param path file path
     * @throws IOException if file exists or path is not valid
     * @throws IllegalArgumentException if file path null
     */
    public static void createFile(String path) throws IOException, IllegalArgumentException {
    	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.createFile] ---> Path cannot be null.");
    	}
    	
        Path filePath = Paths.get(path);
        if (Files.exists(filePath)) {
            throw new IOException("[FileFolderManager.createFile] ---> File already exists at path '" + path + "'.");
        }
        try {
            Files.createFile(filePath);
        } 
        catch (IOException exception) {
            throw new IOException("[FileFolderManager.createFile] ---> Failed to create file at path '" + path + "' ---> " + exception);
        }
    }
    /**
     * Deletes a file.
     *
     * @param path file path
     * @throws IOException if file does not exist
     * @throws IllegalArgumentException if file path null
     */
    public static void deleteFile(String path) throws IOException, IllegalArgumentException {
    	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.deleteFile] ---> Path cannot be null.");
    	}
    	
        Path filePath = Paths.get(path);
        if (!Files.isRegularFile(filePath)) {
            throw new IOException("[FileFolderManager.deleteFile] ---> File does not exist or is not a regular file at path '" + path + "'.");
        }
        try {
            Files.delete(filePath);
        } 
        catch (IOException exception) {
            throw new IOException("[FileFolderManager.deleteFile] ---> Failed to delete file at path '" + path + "' ---> " + exception);
        }
    }
    /**
     * Checks if file exists.
     *
     * @param path file path
     * @throws IllegalArgumentException if file path null
     * @return true if exists
     */
    public static boolean isFileExists(String path) throws IllegalArgumentException {
    	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.isFileExists] ---> Path cannot be null.");
    	}
    	
        Path filePath = Paths.get(path);
        return Files.isRegularFile(filePath);
    }
    /**
     * Lists files in folder.
     *
     * @param path folder path
     * @throws IllegalArgumentException if file path null
     * @return array of file names (not including sub directories)
     */
    public static String[] listFilesInFolder(String path) throws IllegalArgumentException {
    	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.listFilesInFolder] ---> Path cannot be null.");
    	}
    	
        File folder = new File(path);
        if (!folder.isDirectory()) {
            return new String[0];
        }
        File[] files = folder.listFiles(File::isFile);
        if (files == null) {
            return new String[0];
        }
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }
    /**
     * Reads file content.
     *
     * @param path file path
     * @throws IOException if file does not exist or cannot be read
     * @throws IllegalArgumentException if file path null
     * @return file content as a string
     */
    public static String readFile(String path) throws IOException, IllegalArgumentException {
    	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.readFile] ---> Path cannot be null.");
    	}
    	
        Path filePath = Paths.get(path);
        if (!Files.isRegularFile(filePath)) {
            throw new IOException("[FileFolderManager.readFile] ---> File does not exist at path '" + path + "'.");
        }
        try {
            return new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        } 
        catch (IOException exception) {
            throw new IOException("[FileFolderManager.readFile] ---> Failed to read file at path '" + path + "' ---> " + exception);
        }
    }
    /**
     * Writes content to file.
     *
     * @param path    file path
     * @param content text content
     * @throws IOException if file does not exist
     * @throws IllegalArgumentException if file path null
     */
    public static void writeFile(String path, String content) throws IOException, IllegalArgumentException {
      	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.writeFile] ---> Path cannot be null.");
    	}
        Path filePath = Paths.get(path);
        if (!Files.isRegularFile(filePath)) {
            throw new IOException("[FileFolderManager.writeFile] ---> File does not exist at path '" + path + "'.");
        }
        try {
            Files.write(filePath, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } 
        catch (IOException exception) {
            throw new IOException("[FileFolderManager.writeFile] ---> Failed to write to file at path '" + path + "' ---> " + exception);
        }
    }

    
    /**
     * Creates a folder.
     *
     * @param path folder path
     * @throws IOException if folder exists or path is not valid
     * @throws IllegalArgumentException if folder path null
     */
    public static void createFolder(String path) throws IOException, IllegalArgumentException {
      	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.createFolder] ---> Path cannot be null.");
    	}
      	
        Path folderPath = Paths.get(path);
        if (Files.exists(folderPath)) {
            throw new IOException("[FileFolderManager.createFolder] ---> Folder already exists at path '" + path + "'.");
        }
        try {
            Files.createDirectory(folderPath);
        } 
        catch (IOException exception) {
            throw new IOException("[FileFolderManager.createFolder] ---> Failed to create folder at path '" + path + "' ---> " + exception);
        }
    }
    /**
     * Checks if folder exists.
     *
     * @param path folder path
     * @throws IllegalArgumentException if folder path null
     * @return true if exists and is a directory
     */
    public static boolean isFolderExists(String path) throws IllegalArgumentException {
      	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.isFolderExists] ---> Path cannot be null.");
    	}
      	
        Path folderPath = Paths.get(path);
        return Files.isDirectory(folderPath);
    }
    /**
     * Lists folders in folder.
     *
     * @param path folder path
     * @throws IllegalArgumentException if folder path null
     * @return array of sub folder names
     */
    public static String[] listFoldersInFolder(String path) throws IllegalArgumentException {
      	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.listFoldersInFolder] ---> Path cannot be null.");
    	}
      	
        File folder = new File(path);
        if (!folder.isDirectory()) {
            return new String[0];
        }
        File[] dirs = folder.listFiles(File::isDirectory);
        if (dirs == null) {
            return new String[0];
        }
        String[] names = new String[dirs.length];
        for (int i = 0; i < dirs.length; i++) {
            names[i] = dirs[i].getName();
        }
        return names;
    }
    /**
     * Deletes a folder.
     *
     * @param path folder path
     * @throws IOException if folder does not exist or cannot be deleted (e.g., not empty)
     * @throws IllegalArgumentException if folder path null
     */
    public static void deleteFolder(String path) throws IOException, IllegalArgumentException{
      	if (path == null) {
    	    throw new IllegalArgumentException("[FileFolderManager.deleteFolder] ---> Path cannot be null.");
    	}
      	
        Path folderPath = Paths.get(path);
        if (!Files.isDirectory(folderPath)) {
            throw new IOException("[FileFolderManager.deleteFolder] ---> Folder does not exist or is not a directory at path '" + path + "'.");
        }
        try {
            Files.delete(folderPath);
        } 
        catch (IOException exception) {
            throw new IOException("[FileFolderManager.deleteFolder] ---> Failed to delete folder at path '" + path + "' ---> " + exception);
        }
    }
    
    
    /**
     * Safely combines a base path with a continuation path.
     * Normalizes the result and prevents path traversal attacks (e.g., "../").
     *
     * @param basis       the base directory path (acts as the root)
     * @param continuation the subpath to append (may contain relative elements like ".." or ".")
     * @return the normalized combined path as a string
     * @throws IOException if the resulting path lies outside the base directory
     * @throws IllegalArgumentException if any path null
     */
    public static String combinePaths(String basis, String continuation) throws IOException {
        if (basis == null || continuation == null) {
            throw new IllegalArgumentException("[FileFolderManager.combinePaths] ---> Basis or continuation cannot be null.");
        }
        
        Path basePath = Paths.get(basis).toAbsolutePath().normalize();
        Path combined = basePath.resolve(continuation).normalize();

        if (!combined.startsWith(basePath)) {
            throw new IOException("[FileFolderManager.combinePaths] ---> Path traversal detected '" + continuation + "' would escape base directory '" + basis + "'");
        }
        return combined.toString();
    }
}


