package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Controller for the Main, Register, and Login Tab
 *
 * @author Warren Zhu, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class AccountController {

    private final AccountStorage accountStorage;
    private final ItemStorage itemStorage;
    private final StorageGateway storageGateway;
    private final String username;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public AccountController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.username = username;
        this.storageGateway = storageGateway;
        StorageFactory storageFactory = new StorageFactory();
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
        itemStorage = (ItemStorage) storageFactory.getStorage(storageGateway, StorageEnum.ITEM);
    }

    public String getEmail() throws AccountNotFoundException {
        return accountStorage.getEmail(username);
    }

    private List<HashMap<String, String>> getInventory() throws ItemNotFoundException {
        return itemStorage.getVerifiedInventoryData(username);
    }

    private List<HashMap<String, String>> getWishlist() throws ItemNotFoundException {
        return itemStorage.getWishlistData(username);
    }

    private List<String> getStatuses() throws AccountNotFoundException, WrongAccountTypeException {
        return accountStorage.getAccountStatuses(username);
    }

    // consider making this into a presenter class
    public String getStatusString() throws AccountNotFoundException, WrongAccountTypeException {
        List<String> userStatusList = getStatuses();
        StringBuilder statusString = new StringBuilder();
        for (String s : userStatusList) {
            statusString.append(s).append(" | ");
        }
        return statusString.toString();
    }

    /**
     * Get inventory items in a String
     *
     * @return inventory String
     */
    public String getInventoryString() throws ItemNotFoundException {
        StringBuilder str = new StringBuilder();
        for (Map<String, String> map : getInventory()) {
            str.append("Item ID: ").append(map.get("id")).append("\n");
            str.append(map.get("name")).append(": ").append(map.get("description")).append("\n");
            str.append("Tags: ").append(map.get("tags")).append("\n\n");
        }
        return str.toString();
    }

    /**
     * Get wishlist items in a string
     *
     * @return wishlist string
     */
    public String getWishlistString() throws ItemNotFoundException {
        StringBuilder str = new StringBuilder();
        for (Map<String, String> map: getWishlist()) {
            str.append("Item ID: ").append(map.get("id")).append("\n");
            str.append("Item Owner: ").append(map.get("owner")).append("\n");
            str.append(map.get("name")).append(": ").append(map.get("description")).append("\n");
            str.append("Tags: ").append(map.get("tags")).append("\n\n");
        }
        return str.toString();
    }

    public boolean isAway() throws AccountNotFoundException, WrongAccountTypeException {
        List<String> userStatusList = getStatuses();
        for (int i = 0; i < userStatusList.size(); i++){
            if (userStatusList.contains("AWAY")){
                return true;
            }
        }
        return false;
    }

    public void setAwayStatus() throws AccountNotFoundException, WrongAccountTypeException, IOException {
        accountStorage.createStatus(username, "AWAY");
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    public void removeAwayStatus() throws StatusNotFoundException, WrongAccountTypeException, AccountNotFoundException, IOException {
        accountStorage.removeStatus(username, "AWAY");
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }
}