package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import javax.swing.*;
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

    /**
     * Initializes a new BrowseController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public BrowseController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
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

    public void displayListings(JTextArea txtArea) throws ItemNotFoundException {
        List<HashMap<String, String>> listingList = itemStorage.getVerifiedItemsData();
        for (HashMap<String, String> stringStringHashMap : listingList) {
            for (String str : stringStringHashMap.keySet()) {
                txtArea.append(str + stringStringHashMap.get(str) + "\n");
            }
        }
    }
}
