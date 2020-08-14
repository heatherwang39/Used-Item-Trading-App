package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.WrongAccountTypeException;
import main.java.system.StorageDepot;
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

    /**
     * Initializes a new UserlistController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     */
    public UserlistController(StorageDepot storageDepot) {
        storageGateway = storageDepot.getStorageGateway();
        accountStorage = storageDepot.getAccountStorage();
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
     * @throws AccountNotFoundException account was not found
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
     * @throws IOException file cannot be read/written
     * @throws AccountNotFoundException account not found
     * @throws WrongAccountTypeException wrong account type
     */
    public void muteUser(String username) throws IOException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.createStatus(username, "MUTED");
        storageGateway.saveStorageData();
    }
}
