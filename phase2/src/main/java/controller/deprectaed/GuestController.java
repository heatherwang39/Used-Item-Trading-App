package main.java.controller.deprectaed;

import main.java.controller.deprectaed.AccountController;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A Controller for Guest Accounts
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class GuestController implements AccountController {
    protected final ItemStorage itemStorage;
    protected final StorageGateway storageGateway;

    /** Class Constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    GuestController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory storageFactory = new StorageFactory();
        itemStorage = (ItemStorage) storageFactory.getStorage(storageGateway, StorageEnum.ITEM);
    }


    /** Return the type of account that is linked with this controller
     *
     * @return The account type linked with this controller
     */
    public String getAccountType(){ return "GUEST";}


    /**
     * Get all the data of verified Items in the overall list of Items
     *
     * @return all data of verified Items
     */
    public List<HashMap<String, String>> getVerifiedItemsData() throws ItemNotFoundException {
        return itemStorage.getVerifiedItemsData();
    }

    /**
     *
     * @param username
     * @return
     */
    public List<HashMap<String, String>> getVerifiedInventoryData(String username) throws ItemNotFoundException {
        return itemStorage.getVerifiedInventoryData(username);
    }

    /**
     *
     * @param searchTerms
     * @param tags
     * @return
     */
    public List<HashMap<String, String>> getVerifiedInventoryData(List<String> searchTerms, List<String> tags) throws ItemNotFoundException {
        return itemStorage.getVerifiedInventoryData(searchTerms, tags);
    }

    /**
     *
     * @param username
     * @return
     */
    public List<HashMap<String, String>> getWishlistData(String username) throws ItemNotFoundException {
        return itemStorage.getWishlistData(username);
    }
}