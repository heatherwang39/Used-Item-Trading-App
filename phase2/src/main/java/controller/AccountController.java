package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.Item;
import main.java.model.item.ItemStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.List;

/**
 * A Controller for the Main, Register, and Login Tab
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class AccountController {

    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;
    private ItemStorage itemStorage;
    private final String username;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public AccountController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory storageFactory = new StorageFactory();
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
    }

    public String getEmail() throws AccountNotFoundException {
        return accountStorage.getEmail(username);
    }

    public List<String> getInventory(){
        return itemStorage.getInventoryString(username);
    }

    public List<String> getWishlist(){
        return itemStorage.getWishlistString(username);
    }
}