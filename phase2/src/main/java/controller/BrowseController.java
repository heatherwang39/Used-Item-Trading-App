package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system.StorageDepot;

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
     * @param storageDepot storageDepot associated with the program
     */
    public BrowseController(StorageDepot storageDepot) {
        itemStorage = storageDepot.getItemStorage();
    }

    /**
     * Return the data of all verified items
     *
     * @return items data
     * @throws ItemNotFoundException item isn't found
     */
    public List<HashMap<String, String>> getVerifiedItems() throws ItemNotFoundException {
        return itemStorage.getBrowsableItemsData();
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
            str.append("Tags: ").append(map.get("tags")).append("\n\n");
        }
        return str.toString();
    }



}
