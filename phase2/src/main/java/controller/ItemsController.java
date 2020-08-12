package main.java.controller;

import main.java.model.item.*;
import main.java.presenter.ItemPresenter;
import main.java.system.StorageDepot;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
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
    private final String username;
    private ItemPresenter itemPresenter;

    /**
     * Initializes a new AddItemsController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the AddItems tab
     * @throws IOException file can't be read/written
     * @throws ClassNotFoundException serialized class not found
     */
    public ItemsController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageDepot sd = new StorageDepot(storageGateway);
        itemStorage = sd.getItemStorage();
        itemPresenter = new ItemPresenter();
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
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
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
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
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
        storageGateway.saveStorageData(StorageEnum.ITEM);
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
     * @throws IOException file can't be read/written
     */
    public void unhideItem(String itemID) throws AlreadyNotHiddenException, ItemNotFoundException, IOException {
        itemStorage.unhideItem(Integer.parseInt(itemID));
        storageGateway.saveStorageData(StorageEnum.ITEM);
    }

}
