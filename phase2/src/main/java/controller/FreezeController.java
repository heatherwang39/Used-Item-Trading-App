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
 * @author Fadi Hareth, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class FreezeController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final StatusStorage statusStorage;
    private final AccountStorage accountStorage;


    /**
     * Class constructor for FreezeController
     *
     * @param storageGateway gateway for loading and saving information
     */
    public FreezeController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("TRADE"));
        statusStorage = (StatusStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("STATUS"));
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ACCOUNT"));
    }

    /**
     * Show all the users that need to be frozen based on threshold and set their status to be frozen
     * @param borrowThreshold threshold for borrowing more than lending
     * @param incompleteThreshold threshold for too many incomplete trades
     * @param weeklyThreshold threshold for too many trades in one week
     * @throws InvalidStatusTypeException Invalid status, not in system
     * @return the frozen users and the frozen reasons
     */
    public List<List<String>> showAllFrozenUsers(int borrowThreshold, int incompleteThreshold, int weeklyThreshold) throws InvalidStatusTypeException, IOException {
        List<String> allUsers = accountStorage.getUsernames();
        List<List<String>> frozenUsersAndReasons = tradeStorage.showFreezeUsers(allUsers, borrowThreshold, incompleteThreshold, weeklyThreshold);
        for(int i=0; i<frozenUsersAndReasons.size();i++){
            statusStorage.createStatus(frozenUsersAndReasons.get(i).get(0),"FROZEN");
        }
        storageGateway.saveStorageData(StorageEnum.STATUS);
        return frozenUsersAndReasons;
    }

    /**
     * Sets a certain user's status to frozen
     *
     * @param username username of the user
     * @throws InvalidStatusTypeException Invalid status, not in system
     */
    public void freezeUser(String username) throws InvalidStatusTypeException, IOException {
        statusStorage.createStatus(username, "FREEZE");
        storageGateway.saveStorageData(StorageEnum.valueOf("STATUS"));
    }

    /**
     * Removes the freeze status from a certain user
     *
     * @param username username of the user
     * @throws StatusNotFoundException Invalid status, not in system
     */
    public void unfreezeUser(String username) throws StatusNotFoundException, IOException {
        statusStorage.removeStatus(username, "FREEZE");
        storageGateway.saveStorageData(StorageEnum.valueOf("STATUS"));
    }



}
