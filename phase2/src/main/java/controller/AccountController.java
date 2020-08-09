package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusNotFoundException;
import main.java.model.status.StatusStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.List;

/**
 * A Controller for the Main, Register, and Login Tab
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class AccountController {

    private final AccountStorage accountStorage;
    private final ItemStorage itemStorage;
    private final StatusStorage statusStorage;
    private final String username;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public AccountController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.username = username;

        StorageFactory storageFactory = new StorageFactory();
        statusStorage = (StatusStorage) storageFactory.getStorage(storageGateway, StorageEnum.STATUS);
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
        itemStorage = (ItemStorage) storageFactory.getStorage(storageGateway, StorageEnum.ITEM);
    }

    public String getEmail() throws AccountNotFoundException {
        return accountStorage.getEmail(username);
    }

    public List<String> getInventory(){
        return itemStorage.getInventoryString(username);
    }

    public List<String> getWishlist(){
        return itemStorage.getWishlistString(username);
    }

    private List<String> getStatuses(){
        return statusStorage.getAccountStatusStrings(username);
    }

    // consider making this into a presenter class
    public String getStatusString(){
        List<String> userStatusList = getStatuses();
        String statusString = null;
        for (String s : userStatusList) {
            statusString += (s + " | ");
        }
        return statusString;
    }

    public boolean isAway(){
        List<String> userStatusList = getStatuses();
        for (int i = 0; i < userStatusList.size(); i++){
            if (userStatusList.contains("AWAY")){
                return true;
            }
        }
        return false;
    }

    public void setAwayStatus() throws InvalidStatusTypeException {
        statusStorage.createStatus(username, "AWAY");
    }

    public void removeAwayStatus() throws StatusNotFoundException {
        statusStorage.removeStatus(username, "AWAY");
    }
}