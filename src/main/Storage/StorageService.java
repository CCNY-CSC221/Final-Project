/**
 * Manages user storage system.
 */
public class StorageService {

    /** Base storage directory */
    public static final String BASE_STORAGE_PATH = "";
    
    public static UserStorage loadUserStorage(String username) {
        if(!(isUserStorageSpaceExists(user.getUsername()))) {
            throw new IllegalArgumentException("Failed..." + username);
        }
        String path = getUserstorageSpacePath(username);
        return null;
    }
    public static void saveUserStorage(UserStorage user) {
        if(!(isUserStorageSpaceExists(user.getUsername()))) {
            throw new IllegalArgumentException("Failed..." + username);
        }
        String path = getUserstorageSpacePath(user.getUsername());
    }
    public static String getUserStorageSpacePath(String username) {
        if(!(isUserStorageSpaceExists(user.getUsername()))) {
            throw new IllegalArgumentException("Failed..." + username);
        }
        return BASE_STORAGE_PATH + username +"/";
    }
    public static boolean isUserStorageSpaceExists(String username) {
        try {
            return FileFolderManager.isFolderExists(BASE_STORAGE_PATH + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createUserStorageSpace(String username) {
        try {
            FileFolderManager.createFolder(BASE_STORAGE_PATH + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    public static void deleteUserStorageSpace(String username) {
        try {
            FileFolderManager.deleteFolder(BASE_STORAGE_PATH + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
