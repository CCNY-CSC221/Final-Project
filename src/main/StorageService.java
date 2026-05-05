//@author Kent
// Import external dependencies
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Manages user storage system.
 */
public class StorageService {

    /** Base storage directory */
    public static final String BASE_STORAGE_PATH = "UsersStorage";

    /**
     * Loads user storage.
     *
     * @param userName user name
     * @return user storage
     * @throws IllegalArgumentException if user storage not exist or user name not correct
     */
    public static UserStorage loadUserStorage(String userName) throws IllegalArgumentException {
    	if (userName == null) {
    	    throw new IllegalArgumentException("[StorageService.loadUserStorage] ---> User name cannot be null.");
    	}
    	
    	if (!StorageService.isUserStorageSpaceExists(userName)) {
    		throw new IllegalArgumentException("[StorageService.loadUserStorage] ---> User name storage not exist.");
    	}
    	
    	String userStoragePath;
    	try {
    	    userStoragePath = FileFolderManager.combinePaths(BASE_STORAGE_PATH, userName);
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.loadUserStorage] ---> User " + userName + "hack attempt was detected.");
        }
    	
    	String[] availableYearlyLedgerNames = FileFolderManager.listFilesInFolder(userStoragePath);
    	ArrayList<TransactionLedger> yearlyLedgers = new ArrayList<TransactionLedger>();
    	for (String yearlyLedgerName : availableYearlyLedgerNames) {
    	    if (!yearlyLedgerName.endsWith(".csv")) {
    	        continue;
    	    }
    	    
    		String yearlyLedgerPath;
    		try {
    			yearlyLedgerPath = FileFolderManager.combinePaths(userStoragePath, yearlyLedgerName);
    		}
    		catch (Exception exception) {
                throw new IllegalArgumentException("[StorageService.loadUserStorage] ---> User " + userName + "hack attempt was detected.");
            }
    		
    		String yearlyLedgerText;
    		try {
				yearlyLedgerText = FileFolderManager.readFile(yearlyLedgerPath);
			} catch (IOException exception) {
				throw new IllegalArgumentException("[StorageService.loadUserStorage] ---> Problem reading file ---> " + exception);
			}
    		
    		TransactionLedger yearlyLedger = TransactionLedger.createFromCSVText(yearlyLedgerText);
    		yearlyLedgers.add(yearlyLedger);
    	}
    	
    	UserStorage userStorage;
    	try	{
    		userStorage = new UserStorage(userName);
    		for (TransactionLedger ledger : yearlyLedgers) {
    			userStorage.addLedger(ledger);
    		}
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.loadUserStorage] ---> Initialization problem --->" + exception);
        }
  
    	return userStorage;
    }

    /**
     * Saves user storage.
     *
     * @param user user storage
     * @throws IllegalArgumentException if user storage not exist or user is null
     */
    public static void saveUserStorage(UserStorage user) throws IllegalArgumentException {
    	if (user == null) {
    	    throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> User cannot be null.");
    	}
    	
    	if (!StorageService.isUserStorageSpaceExists(user.getUserName())) {
    		throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> User name storage not exist.");
    	}
    	
    	String userStoragePath;
    	try {
    	    userStoragePath = FileFolderManager.combinePaths(BASE_STORAGE_PATH, user.getUserName());
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> User " + user.getUserName() + "hack attempt was detected.");
        }
    	
       	String[] availableYearlyLedgerNames = FileFolderManager.listFilesInFolder(userStoragePath);
    	for (String yearlyLedgerName : availableYearlyLedgerNames) {
     		String yearlyLedgerPath;
    		try {
    			yearlyLedgerPath = FileFolderManager.combinePaths(userStoragePath, yearlyLedgerName);
    		}
    		catch (Exception exception) {
                throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> User " + user.getUserName() + "hack attempt was detected.");
            }  
    		
    		try {
    			FileFolderManager.deleteFile(yearlyLedgerPath);
    		}
    		catch (Exception exception) {
    			throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> Problem deleting old file ---> " + exception);
    		}
    		
    	}
    	
    	Map<Integer, TransactionLedger> yearlyLedgers = user.getYearlyLedgers();
    	for (Map.Entry<Integer, TransactionLedger> entry : yearlyLedgers.entrySet()) {
    	    Integer year = entry.getKey();
    	    TransactionLedger yearlyLedger = entry.getValue();
    	    String yearlyLedgerPath;
    		try {
    			String yearlyLedgerName = year + ".csv";
    			yearlyLedgerPath = FileFolderManager.combinePaths(userStoragePath, yearlyLedgerName);
    		}
    		catch (Exception exception) {
                throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> User " + user.getUserName() + "hack attempt was detected.");
            }  
    		
    		try {
    			FileFolderManager.createFile(yearlyLedgerPath);
    			FileFolderManager.writeFile(yearlyLedgerPath, yearlyLedger.transformToCSVText());
    		}
    		catch (Exception exception) {
    			throw new IllegalArgumentException("[StorageService.saveUserStorage] ---> Problem creating new file ---> " + exception);
    		}
    	}
    }

    /**
     * Checks if user storage exists.
     *
     * @param userName user name
     * @throws IllegalArgumentException if user name not correct
     * @return true if exists
     */
    public static boolean isUserStorageSpaceExists(String userName) throws IllegalArgumentException {
    	if (userName == null) {
    	    throw new IllegalArgumentException("[StorageService.isUserStorageSpaceExists] ---> User name cannot be null.");
    	}
    	
    	String[] availableUsersNames = FileFolderManager.listFoldersInFolder(BASE_STORAGE_PATH);
    	return Arrays.asList(availableUsersNames).contains(userName);
    }

    /**
     * Creates user storage folder.
     *
     * @param userName user name
     * throws IllegalArgumentException if a problem arises with creation storage folder or user name not correct
     */
    public static void createUserStorageSpace(String userName) throws IllegalArgumentException {
    	if (userName == null) {
    	    throw new IllegalArgumentException("[StorageService.createUserStorageSpace] ---> User name cannot be null.");
    	}
    	
    	if (StorageService.isUserStorageSpaceExists(userName)) {
    		return;
    	} 
    	
    	String userStoragePath;
    	try {
    	    userStoragePath = FileFolderManager.combinePaths(BASE_STORAGE_PATH, userName);
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.createUserStorageSpace] ---> User " + userName + "hack attempt was detected.");
        }
    	
    	try {
    		FileFolderManager.createFolder(userStoragePath);
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.createUserStorageSpace] ---> Problem creating new folder ---> " + exception);
        }
    }

    /**
     * Deletes user storage folder.
     *
     * @param userName user name
     * throws IllegalArgumentException if a problem arises with deletion storage folder or user name not correct
     */
    public static void deleteUserStorageSpace(String userName) throws IllegalArgumentException {
    	if (userName == null) {
    	    throw new IllegalArgumentException("[StorageService.deleteUserStorageSpace] ---> User name cannot be null.");
    	}
    	
    	if (!StorageService.isUserStorageSpaceExists(userName)) {
    		return;
    	} 
    	
    	String userStoragePath;
    	try {
    	    userStoragePath = FileFolderManager.combinePaths(BASE_STORAGE_PATH, userName);
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.deleteUserStorageSpace] ---> User " + userName + "hack attempt was detected.");
        }
    	
    	String[] files = FileFolderManager.listFilesInFolder(userStoragePath);
        for (String fileName : files) {
            try {
                String filePath = FileFolderManager.combinePaths(userStoragePath, fileName);
                FileFolderManager.deleteFile(filePath);
            } catch (Exception exception) {
                throw new IllegalArgumentException("[StorageService.deleteUserStorageSpace] ---> Problem deleting file: " + fileName + " ---> " + exception);
            }
        }
        
    	try {
    		FileFolderManager.deleteFolder(userStoragePath);
    	}
        catch (Exception exception) {
            throw new IllegalArgumentException("[StorageService.deleteUserStorageSpace] ---> Problem deleting old folder ---> " + exception);
        }
    }
}
