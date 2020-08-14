package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.trade.*;
import main.java.system.StorageDepot;
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
public class TradeRequestController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final ItemStorage itemStorage;
    private final AccountStorage accountStorage;
    private final String username;

    /**
     * Initializes a new TradeRequestController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     * @param username username of the user accessing the Request tab
     */
    public TradeRequestController(StorageDepot storageDepot, String username) {
        storageGateway = storageDepot.getStorageGateway();
        this.username = username;
        tradeStorage = storageDepot.getTradeStorage();
        itemStorage = storageDepot.getItemStorage();
        accountStorage = storageDepot.getAccountStorage();
    }

    /**
     * Creates a new Trade request using the given inputs by the user
     *
     * @param permanent Whether the trade is permanent or not
     * @param tradeAlgorithmName The name of the Trade Algorithm that is associated with this trade
     * @param items list of itemIDs part of request
     * @throws ItemNotFoundException Thrown if there is an invalid itemID in items
     * @throws NoSuchTradeAlgorithmException Thrown if no tradeAlgorithm has the given name
     * @throws IOException file cannot be read/written
     * @throws WrongTradeAccountException attempted to edit trade through wrong account
     * @throws TradeNumberException no trade has the given number
     * @throws TradeCancelledException trade has been cancelled
     * @throws ItemAlreadyInActiveTradeException An Item is already in another active Trade
     */
    public void createRequest(boolean permanent, TradeAlgorithmName tradeAlgorithmName, List<Integer> items)
            throws ItemNotFoundException, NoSuchTradeAlgorithmException, IOException, WrongTradeAccountException, TradeNumberException, TradeCancelledException, ItemAlreadyInActiveTradeException {
        List<String> traders = new ArrayList<>();
        traders.add(username);
        for (Integer itemID : items) {
            if (itemID != null) {
                String owner = itemStorage.getData(itemID).get("owner");
                if (!owner.equals(username)) traders.add(owner);
            }
        }
        int tradeNumber = tradeStorage.newTrade(permanent, tradeAlgorithmName, traders, items);
        tradeStorage.acceptTrade(tradeNumber, username);
        storageGateway.saveStorageData();
    }

    /**
     * Check if trade request is valid
     *
     * @param str1 requested item
     * @param str2 offered item
     * @return VALID if the request is valid, if not, it will return a message to display
     * @throws ItemNotFoundException item was not found
     */
    public String checkValidRequest(String str1, String str2) throws ItemNotFoundException {
        if (str1.isEmpty()) return "You need to input what item you would like to request";
        try {
            String owner1 = itemStorage.getData(Integer.parseInt(str1)).get("owner");
            if (owner1 != null && owner1.equals(username))
                return "The item you are trying to request belongs to you!";
            if (!str2.isEmpty()) {
                String owner2 = itemStorage.getData(Integer.parseInt(str2)).get("owner");
                if (owner2 != null && !owner2.equals(username))
                    return "The item you are trying to offer does not belong to you!";
            }
        } catch (NumberFormatException e){
            return "At least one of your inputs is not a number";
        }
        return "VALID";
    }

    /**
     * Find all trade suggestions
     * @return List of trade suggestions data
     * @throws ItemNotFoundException item was not found
     */
    public List<List<HashMap<String, String>>> suggestAllItems() throws ItemNotFoundException {
        // iterate through every user in the system and find what currentUser can lend them
        // iterate through every user in the system and find what they can lend currentUser
        // get the intersection of these two lists.
        // goal: return a list (unformatted) of every item currentUser could lend to another user in the system,
        // but only when that other user has something for currentUser

        List<List<HashMap<String, String>>> suggestedItemsToLendList = new ArrayList<>();
        List<String> userList = accountStorage.getUsers();
        userList.remove(username);
        List<HashMap<String, String>> currentUserLendList = new ArrayList<>();
        List<HashMap<String, String>> otherUserLendList = new ArrayList<>();
        for (String otherUser : userList) {
            currentUserLendList = itemStorage.suggestItems(username, otherUser); // list of items currentUser could lend otherUser
            otherUserLendList = itemStorage.suggestItems(otherUser, username); // list of items otherUser could lend currentUser
        }
        for (HashMap<String, String> item1: currentUserLendList) {
            for (HashMap<String, String> item2: otherUserLendList) {
                suggestedItemsToLendList.add(Arrays.asList(item1, item2));
            }
        }
        return suggestedItemsToLendList;

    }
}
