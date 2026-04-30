/**
 * Manages user storage system.
 */
public class StorageService {

    /** Base storage directory */
    public static final String BASE_STORAGE_PATH = "";
    
    public static UserStorage loadUserStorage(String username) {
        if(!(isUserStorageSpaceExists(username) {
            throw new IllegalArgumentException("Failed..." + username);
        }
        String path = getUserStorageSpacePath(username);
        return null;
    }
    public static void saveUserStorage(UserStorage user) {
        if(!(isUserStorageSpaceExists(user.getUsername()))) {
            throw new IllegalArgumentException("Failed..." + user.getUsername());
        }
        String path = getUserStorageSpacePath(user.getUsername());
    }
    public static String getUserStorageSpacePath(String username) {
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
           return FileFolderManager.createFolder(BASE_STORAGE_PATH + username);
    }    
    public static void deleteUserStorageSpace(String username) {
        try {
            FileFolderManager.deleteFolder(BASE_STORAGE_PATH + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
