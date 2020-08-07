package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;
import java.util.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * Controller that returns the needed information for GUI client to display for Requests tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class RequestsController {
    private final StorageGateway storageGateway;
    private final ItemStorage itemStorage;
    private final String username;

    /**
     * Initializes a new RequestsController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the requests tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public RequestsController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    /**
     * Gets a list containing HashMaps of data of all Trades that the user has requested
     *
     * @return List of Trade datas
     * @throws ItemNotFoundException An item not in the system was inputted
     */
    public List<HashMap<String, String>> getRequests() throws ItemNotFoundException {
        return itemStorage.getUnverifiedItemsData();
    }

    /**
     * Verifies the item with given itemID
     *
     * @param itemID ID of the item
     * @throws ItemNotFoundException invalid itemID
     * @throws IOException
     */
    public void verifyItem(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.verifyItem(itemID);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }

    /**
     * Rejects the item with given itemID
     *
     * @param itemID ID of the item
     * @throws ItemNotFoundException invalid itemID
     * @throws IOException
     */
    public void rejectItem(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.removeItem(itemID);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }
}
