package main.java.controllers;

import main.java.model.item.ItemStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Controller for Guest Accounts
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class GuestController implements AccountController{
    private ItemStorage itemStorage;

    /** Class Constructor
     *
     * @param itemStorage All the items stored by this given controller
     */
    GuestController(ItemStorage itemStorage){
        this.itemStorage = itemStorage;
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
    public List<HashMap<String, String>> getVerifiedItemsData(){
        return itemStorage.getVerifiedItemsData();
    }

    /**
     *
     * @param username
     * @return
     */
    public List<HashMap<String, String>> getVerifiedInventoryData(String username){
        return itemStorage.getVerifiedInventoryData(username);
    }

    /**
     *
     * @param searchTerms
     * @param tags
     * @return
     */
    public List<HashMap<String, String>> getVerifiedInventoryData(List<String> searchTerms, List<String> tags){
        return itemStorage.getVerifiedInventoryData(searchTerms, tags);
    }

    /**
     *
     * @param username
     * @return
     */
    public List<HashMap<String, String>> getWishlistData(String username){
        return itemStorage.getWishlistData(username);
    }
}