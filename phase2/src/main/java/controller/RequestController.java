package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.trade.NoSuchTradeAlgorithmException;
import main.java.model.trade.TradeAlgorithmName;
import main.java.model.trade.TradeStorage;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;
import java.util.*;

import java.io.IOException;

/**
 * Controller that returns the needed information for GUI client to display for Request tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class RequestController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final ItemStorage itemStorage;
    private final AccountStorage accountStorage;
    private final String username;

    /**
     * Initializes a new RequestController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the Request tab
     */
    public RequestController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("TRADE"));
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ACCOUNT"));
    }

    /**
     * Creates a new Trade request using the given inputs by the user
     *
     * @param permanent Whether the trade is permanent or not
     * @param tradeAlgorithmName The name of the Trade Algorithm that is associated with this trade
     * @param items list of itemIDs part of request
     * @throws ItemNotFoundException Thrown if there is an invalid itemID in items
     * @throws NoSuchTradeAlgorithmException Thrown if no tradeAlgorithm has the given name
     */
    public void createRequest(boolean permanent, TradeAlgorithmName tradeAlgorithmName, List<Integer> items)
            throws ItemNotFoundException, NoSuchTradeAlgorithmException, IOException {
        List<String> traders = new ArrayList<>();
        for (Integer itemID : items) {
            traders.add(itemStorage.getData(itemID).get("owner"));
        }
        if (!(traders.contains(username))) traders.add(username);
        tradeStorage.newTrade(permanent, tradeAlgorithmName, traders, items);
        storageGateway.saveStorageData(StorageEnum.valueOf("TRADE"));
    }

    public boolean checkValidRequest(String user, String str1, String str2) throws ItemNotFoundException {
        if (str1.isEmpty() && str2.isEmpty()){
            return false;
        } else if (str1.isEmpty()){
            try {
                String owner2 = itemStorage.getData(Integer.parseInt(str2)).get("owner");
                return owner2 != null && owner2.equals(user);
            } catch (NumberFormatException e){
                return false;
            }
        } else if (str2.isEmpty()){
            try {
                String owner1 = itemStorage.getData(Integer.parseInt(str1)).get("owner");
                return owner1 != null && !owner1.equals(user);
            } catch (NumberFormatException e){
                return false;
            }
        } else {
            try {
                String owner1 = itemStorage.getData(Integer.parseInt(str1)).get("owner");
                String owner2 = itemStorage.getData(Integer.parseInt(str2)).get("owner");
                return (owner1 != null &&
                        !owner1.equals(user)) &&
                        owner2.equals(user);
            } catch (NumberFormatException e){
                return false;
            }
        }
    }

    public List<List<HashMap<String, String>>> suggestAllItems(String currentUser) throws ItemNotFoundException {
        // iterate through every user in the system and find what currentUser can lend them
        // iterate through every user in the system and find what they can lend currentUser
        // get the intersection of these two lists.
        // goal: return a list (unformatted) of every item username could lend to another user in the system, but only when that other user has something for username

        List<List<HashMap<String, String>>> suggestedItemsToLendList = new ArrayList<>();
        List<String> userList = accountStorage.getUsers();
        userList.remove(currentUser);

        for (String otherUser : userList){
            List<HashMap<String, String>> currentUserLendList = itemStorage.suggestItems(currentUser, otherUser); // list of items currentUser could lend otherUser
            List<HashMap<String, String>> otherUserLendList = itemStorage.suggestItems(otherUser, currentUser); // list of items otherUser could lend currentUser
            if (!currentUserLendList.isEmpty() && !otherUserLendList.isEmpty()){
                suggestedItemsToLendList.add(currentUserLendList);
            }
        }
        return suggestedItemsToLendList;

    }
}
