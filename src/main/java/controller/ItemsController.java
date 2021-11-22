package main.java.controller;

import main.java.model.item.*;
import main.java.model.trade.TradeStorage;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Items tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class ItemsController {
    private final StorageGateway storageGateway;
    private final ItemStorage itemStorage;
    private final TradeStorage tradeStorage;
    private final String username;

    /**
     * Initializes a new AddItemsController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     * @param username username of the user accessing the AddItems tab
     */
    public ItemsController(StorageDepot storageDepot, String username) {
        storageGateway = storageDepot.getStorageGateway();
        this.username = username;
        itemStorage = storageDepot.getItemStorage();
        tradeStorage = storageDepot.getTradeStorage();
    }


    //Methods related to Add Items sub tab

    /**
     * Creates a new item and adds it to the system
     *
     * @param name name of the item
     * @param description description of the item
     * @param tags any user-added search tags of the item
     * @throws IOException file cannot be read/written
     */
    public void addInventoryItem(String name, String description, List<String> tags) throws IOException {
        itemStorage.newItem(username, name, description, tags);
        storageGateway.saveStorageData();
    }

    /**
     * Adds the given item to the user's wishlist
     *
     * @param itemID id of the item
     * @throws ItemNotFoundException invalid item id
     * @throws IOException file cannot be read/written
     */
    public void addWishlistItem(String itemID) throws IOException, ItemNotFoundException {
        itemStorage.addWishList(username, Integer.parseInt(itemID));
        storageGateway.saveStorageData();
    }

    /**
     * Gets whether user owns the item
     * @param user username
     * @param itemID item ID
     * @return if the user owns the item
     * @throws ItemNotFoundException item was not found
     */
    public boolean ownsItem(String user, String itemID) throws ItemNotFoundException {
        return itemStorage.getData(Integer.parseInt(itemID)).get("owner").equals(user);
    }


    //Methods related to Hide Items sub tab

    /**
     * Gets all items in the user's inventory that are not hidden
     *
     * @return List containing HashMaps representing item data
     * @throws ItemNotFoundException invalid itemID found in system
     */
    public List<HashMap<String, String>> getUnhiddenInventory() throws ItemNotFoundException {
        return itemStorage.getUnhiddenInventoryData(username);
    }

    /**
     * Hides the item with given itemID
     *
     * @param itemID id of item
     * @throws AlreadyHiddenException Item is already hidden
     * @throws ItemNotFoundException invalid item id
     * @throws IOException files cannot be read/written
     */
    public void hideItem(String itemID) throws AlreadyHiddenException, ItemNotFoundException, IOException {
        itemStorage.hideItem(Integer.parseInt(itemID));
        storageGateway.saveStorageData();
    }


    //Methods related to Unhide Items sub tab

    /**
     * Gets all items in the user's inventory that are hidden
     *
     * @return List containing HashMaps representing item data
     * @throws ItemNotFoundException invalid itemID found in system
     */
    public List<HashMap<String, String>> getHiddenInventory() throws ItemNotFoundException {
        return itemStorage.getHiddenInventoryData(username);
    }

    /**
     * Unhides the item with given itemID
     *
     * @param itemID id of item
     * @throws AlreadyNotHiddenException Item is already not hidden
     * @throws ItemNotFoundException invalid item id
     * @throws ItemInTradeException item is in an active trade and cannot be unhidden
     * @throws IOException file can't be read/written
     */
    public void unhideItem(String itemID) throws AlreadyNotHiddenException, ItemNotFoundException, ItemInTradeException,
            IOException {
        itemStorage.unhideItem(Integer.parseInt(itemID), tradeStorage.getActiveTradeWithItem(Integer.parseInt(itemID)));
        storageGateway.saveStorageData();
    }

}
