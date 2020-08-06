package main.java.controller;

import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Add Items tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class AddItemsController {
    private final StorageGateway storageGateway;
    private final ItemStorage itemStorage;
    private final String username;

    /**
     * Initializes a new AddItemsController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the requests tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AddItemsController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    /**
     * Creates a new item and adds it to the system
     *
     * @param name name of the item
     * @param description description of the item
     * @param tags any user-added search tags of the item
     * @throws IOException
     */
    public void addItem(String name, String description, List<String> tags) throws IOException {
        itemStorage.newItem(username, name, description, tags);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }
}
