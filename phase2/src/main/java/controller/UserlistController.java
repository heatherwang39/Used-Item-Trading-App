package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusStorage;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.*;

/**
 * Controller that returns the needed information for GUI client to display for Userlist tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class UserlistController {
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;
    private final StatusStorage statusStorage;

    /**
     * Initializes a new UserlistController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     */
    public UserlistController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory sf = new StorageFactory();
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.ACCOUNT);
        statusStorage = (StatusStorage) sf.getStorage(storageGateway, StorageEnum.STATUS);
    }

    /**
     * Returns a list of all users who are not admins
     *
     * @return List of usernames
     */
    public List<String> showUsers() {
        return accountStorage.getUsers();
    }

    /**
     * Returns list of formatted user strings
     * @return username and email
     */
    public List<String> showUserStrings() throws AccountNotFoundException {
        List<String> users = new ArrayList<>();
        for (String user: accountStorage.getUsers()) {
            users.add("Username: (" + user + ") Email Address: (" + accountStorage.getEmail(user) +")\n");
        }
        return users;
    }

    /**
     * Mutes the user with given username
     *
     * @param username username of user
     * @throws InvalidStatusTypeException invalid status type is given
     */
    public void muteUser(String username) throws InvalidStatusTypeException, IOException {
        statusStorage.createStatus(username, "MUTED");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }
}
