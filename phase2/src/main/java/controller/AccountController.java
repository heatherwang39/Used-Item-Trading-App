package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system.StorageDepot;
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
     * @param storageDepot storageDepot associated with the program
     * @param username username of account
     */
    public AccountController(StorageDepot storageDepot, String username) {
        this.username = username;
        storageGateway = storageDepot.getStorageGateway();
        accountStorage = storageDepot.getAccountStorage();
        itemStorage = storageDepot.getItemStorage();
    }

    /**
     * Gets email string
     * @return email address
     * @throws AccountNotFoundException account was not found with username
     */
    public String getEmail() throws AccountNotFoundException {
        return accountStorage.getEmail(username);
    }

    /**
     * gets a list of HashMaps representing items data
     * @return inventory data
     * @throws ItemNotFoundException items were not found
     */
    private List<HashMap<String, String>> getInventory() throws ItemNotFoundException {
        return itemStorage.getVerifiedInventoryData(username);
    }

    /**
     * gets a list of HashMaps representing items data
     * @return wishlist data
     * @throws ItemNotFoundException items were not found
     */
    private List<HashMap<String, String>> getWishlist() throws ItemNotFoundException {
        return itemStorage.getWishlistData(username);
    }

    /**
     * gets account statuses
     * @return list of statuses
     * @throws AccountNotFoundException account was not found
     * @throws WrongAccountTypeException account not supporting statuses was entered
     */
    public List<String> getStatuses() throws AccountNotFoundException, WrongAccountTypeException {
        return accountStorage.getAccountStatuses(username);
    }

    /**
     * gets string containing statuses
     * @return statuses presenter string
     * @throws AccountNotFoundException account was not found
     * @throws WrongAccountTypeException account not supporting statuses was entered
     */
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
     * @throws ItemNotFoundException Thrown when the item is not in system
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

    public String getThresholdsString() throws WrongAccountTypeException, AccountNotFoundException {
        HashMap<String, Integer> thresholds = getCurrentThresholds();
        String formattedString = "";
        formattedString += ("Borrowing Threshold: " + thresholds.get("borrowThreshold") + " item(s) \n") +
                ("Incomplete Trades Threshold: " + thresholds.get("incompleteThreshold") + " trade(s) \n") +
                ("Weekly Trades Threshold: " + thresholds.get("weeklyThreshold") + " trade(s) \n") +
                        ("Gilded Requirement Threshold: " + thresholds.get("gildedThreshold") + " trade(s) \n");
        return formattedString;
    }

    /**
     * Get whether the user has "AWAY" status
     *
     * @return true if the User's statuses contains "AWAY"
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */
    public boolean isAway() throws AccountNotFoundException, WrongAccountTypeException {
        List<String> userStatusList = getStatuses();
        for (int i = 0; i < userStatusList.size(); i++){
            if (userStatusList.contains("AWAY")){
                return true;
            }
        }
        return false;
    }

    /**
     * Add "AWAY" to the user's current statuses
     *
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */
    public void setAwayStatus() throws AccountNotFoundException, IOException, WrongAccountTypeException {
        accountStorage.createStatus(username, "AWAY");
        storageGateway.saveStorageData();
    }

    /**
     * Remove "AWAY" from the user's current statuses
     *
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws StatusNotFoundException when "AWAY" Status with given type could be found
     */
    public void removeAwayStatus() throws StatusNotFoundException, AccountNotFoundException, IOException, WrongAccountTypeException {
        accountStorage.removeStatus(username, "AWAY");
        storageGateway.saveStorageData();
    }

    /** Return all current thresholds of the given account
     *
     * @return A HashMap with key is the threshold's name and value is threshold's number
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public HashMap<String, Integer> getCurrentThresholds() throws WrongAccountTypeException, AccountNotFoundException {
        return accountStorage.getCurrentThresholds(username);
    }

}