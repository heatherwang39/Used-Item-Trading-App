package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Browse tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class BrowseController {
    private final ItemStorage itemStorage;
    private final String username;

    /**
     * Initializes a new BrowseController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the Browse tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public BrowseController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.username = username;
        StorageFactory sf = new StorageFactory();
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    /**
     * Gets all verified items for users to browse
     *
     * @return List of HashMaps that each represent item data
     * @throws ItemNotFoundException incorrect itemID found in system
     */
    public List<HashMap<String, String>> getItems() throws ItemNotFoundException {
        return itemStorage.getVerifiedItemsData();
    }

}
