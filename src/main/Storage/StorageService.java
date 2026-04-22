/**
 * Manages user storage system.
 */
public class StorageService {

    /** Base storage directory */
    public static final String BASE_STORAGE_PATH = "";

    /**
     * Loads user storage.
     *
     * @param username user name
     * @return user storage
     * @throws IllegalArgumentException if user storage not exist
     */
    public static UserStorage loadUserStorage(String username) {}

    /**
     * Saves user storage.
     *
     * @param user user storage
     * @throws IllegalArgumentException if user storage not exist
     */
    public static void saveUserStorage(UserStorage user) {}

    /**
     * Gets storage path.
     *
     * @param username user name
     * @return storage path
     * @throws IllegalArgumentException if user storage not exist
     */
    public static String getUserStorageSpacePath(String username) {}

    /**
     * Checks if user storage exists.
     *
     * @param username user name
     * @return true if exists
     */
    public static boolean isUserStorageSpaceExists(String username) {}

    /**
     * Creates user storage folder.
     *
     * @param username user name
     */
    public static void createUserStorageSpace(String username) {}

    /**
     * Deletes user storage folder.
     *
     * @param username user name
     */
    public static void deleteUserStorageSpace(String username) {}
}