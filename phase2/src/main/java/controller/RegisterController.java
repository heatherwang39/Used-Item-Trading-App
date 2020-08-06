package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusNotFoundException;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Register tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class RegisterController {
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;

    /**
     * Initializes a new FreezeController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public RegisterController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory sf = new StorageFactory();
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ACCOUNT"));
    }

    public void register(String username, String password, String email){

    }

}
