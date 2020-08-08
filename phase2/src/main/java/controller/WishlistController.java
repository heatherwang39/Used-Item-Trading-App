package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.item.NotInWishlistException;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

/**
 * Controller that returns the needed information for GUI client to display for Wishlist tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class WishlistController {
    private final StorageGateway storageGateway;
    private final ItemStorage itemStorage;
    private final String username;

    /**
     * Class constructor for WishlistController
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of user
     */
    public WishlistController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    /**
     * Adds the item with given itemID to user's wishlist
     *
     * @param itemID id of item
     * @throws ItemNotFoundException invalid item id
     */
    public void addWishlist(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.addWishList(username, itemID);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }

    /**
     * Removes the item with givenID from user's wishlist
     *
     * @param itemID id of item
     * @throws NotInWishlistException item is not in user's wishlist
     * @throws ItemNotFoundException invalid item id
     */
    public void removeWishlist(int itemID) throws NotInWishlistException, ItemNotFoundException, IOException {
        itemStorage.removeWishList(username, itemID);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }

    /**
     * Returns the data of all items in the user's wishlist
     *
     * @return List of HashMaps, each representing the data of items
     * @throws ItemNotFoundException there is an invalid item in the system
     */
    public List<HashMap<String, String>> getWishlist() throws ItemNotFoundException {
        return itemStorage.getWishlistData(username);
    }
}
