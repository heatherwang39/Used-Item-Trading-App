package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.List;

/**
 * A Controller to set status and get status
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class GildedController {

    private final StorageGateway storageGateway;
    private final StatusStorage statusStorage;
    private final AccountStorage accountStorage;
    private final TradeStorage tradeStorage;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public GildedController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        StorageFactory storageFactory = new StorageFactory();
        this.storageGateway = storageGateway;
        statusStorage = (StatusStorage)storageFactory.getStorage(storageGateway, StorageEnum.STATUS);
        accountStorage = (AccountStorage)storageFactory.getStorage(storageGateway,StorageEnum.ACCOUNT);
        tradeStorage = (TradeStorage)storageFactory.getStorage(storageGateway, StorageEnum.TRADE);
    }

    /** Get all gilded Users who have completed more than 20 trades and set their status to be gilded in the same time
     *
     * @return the usernames of all gilded Users
     * @throws TradeNumberException an invalid trade number is found
     * @throws InvalidStatusTypeException an invalid type of status if found
     */
    public List<String> getAllGildedUsers() throws TradeNumberException, InvalidStatusTypeException, IOException {
        List<String> allUsers = accountStorage.getUsernames();
        List<String> gildedUsers = tradeStorage.getGildedUsers(allUsers);
        for(String username:gildedUsers){
            statusStorage.createStatus(username,"GILDED");
        }
        storageGateway.saveStorageData(StorageEnum.STATUS);
        return gildedUsers;
    }

    /** Add the gilded status for a certain user
     *
     * @param username the username of the user
     * @throws InvalidStatusTypeException when the type of status is invalid
     */
    public void setGildedStatus(String username) throws InvalidStatusTypeException, IOException {
        statusStorage.createStatus(username,"GILDED");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }

    /** Remove the gilded status for a certain user
     *
     * @param username the username of the user
     * @throws IOException file cannot be read/written
     */
    public void removeGildedStatus(String username) throws IOException {
        statusStorage.removeStatus(username,"GILDED");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }


}
