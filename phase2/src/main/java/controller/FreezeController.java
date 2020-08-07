package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusNotFoundException;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Freeze tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class FreezeController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final StatusStorage statusStorage;
    private final AccountStorage accountStorage;
    private final String username;

    /**
     * Initializes a new FreezeController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the Freeze tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public FreezeController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("TRADE"));
        statusStorage = (StatusStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("STATUS"));
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ACCOUNT"));
    }

    /**
     * Returns all users who have violated a trade threshold but have not been frozen yet
     *
     * @param borrowThreshold threshold for borrowing more than lending
     * @param incompleteThreshold threshold for too many incomplete trades
     * @param weeklyThreshold threshold for too many trades in one week
     * @return a list of lists that contain the username and reasons why that user should be frozen. Empty if there are no users to freeze
     */
    public List<List<String>> showUsersToFreeze(int borrowThreshold, int incompleteThreshold, int weeklyThreshold) {
        List<String> unfrozenUsers = new ArrayList<>();
        List<String> frozenUsers = showFrozenUsers();
        for (String username : accountStorage.getUsernames()) {
            if (!(frozenUsers.contains(username))) unfrozenUsers.add(username);
        }
        return tradeStorage.showFreezeUsers(unfrozenUsers, borrowThreshold, incompleteThreshold, weeklyThreshold);
    }

    /**
     * Shows users who are currently frozen
     *
     * @return list of usernames
     */
    public List<String> showFrozenUsers() {return statusStorage.getAccountsWithStatus("FROZEN"); }

    /**
     * Sets the user's status to frozen
     *
     * @param username username of the user
     * @throws InvalidStatusTypeException Invalid status, not in system
     * @throws IOException
     */
    public void freezeUser(String username) throws InvalidStatusTypeException, IOException {
        statusStorage.createStatus(username, "FREEZE");
        storageGateway.saveStorageData(StorageEnum.valueOf("STATUS"));
    }

    /**
     * Removes the freeze status from a user
     *
     * @param username username of the user
     * @throws StatusNotFoundException Invalid status, not in system
     * @throws IOException
     */
    public void unfreezeUser(String username) throws StatusNotFoundException, IOException {
        statusStorage.removeStatus(username, "FREEZE");
        storageGateway.saveStorageData(StorageEnum.valueOf("STATUS"));
    }

}
