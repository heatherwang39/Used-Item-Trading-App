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
     * @throws IOException file cannot be read/write
     * @throws ClassNotFoundException serialized class not found
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

    /**
     * Return the data of all verified items
     * @return items data
     *
     * @throws ItemNotFoundException item isn't found
     */
    public List<HashMap<String, String>> getVerifiedItems() throws ItemNotFoundException {
        return itemStorage.getVerifiedItemsData();
    }

    /**
     * Return every item that can be browsed in a single string
     *
     * @return browse string
     */
    public String getItemsString() throws ItemNotFoundException {
        StringBuilder str = new StringBuilder();
        for (HashMap<String, String> map : getVerifiedItems()) {
            str.append("Item ID: ").append(map.get("id")).append("\n");
            str.append("Item Owner: ").append(map.get("owner")).append("\n");
            str.append(map.get("name")).append(": ").append(map.get("description")).append("\n");
            str.append("Tags: ").append(": ").append(String.join(", ", map.get("tags"))).append("\n\n");
        }
        return str.toString();
    }



}
