package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusNotFoundException;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * Controller that returns the needed information for GUI client to display for specifically Admin account
 *
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class AdminController extends LoginAccountController {
    private AccountStorage accountStorage;
    private ItemStorage itemStorage;
    private MeetingStorage meetingStorage;
    private StatusStorage statusStorage;
    private TradeStorage tradeStorage;
    private final StorageGateway storageGateway;



    public AdminController(String username, AccountStorage accountStorage, ItemStorage itemStorage,
                           MessageStorage messageStorage, MeetingStorage meetingStorage, StatusStorage statusStorage,
                           TradeStorage tradeStorage, StorageGateway storageGateway){

        super(username, messageStorage, itemStorage, accountStorage);

        this.accountStorage = accountStorage;
        this.itemStorage = itemStorage;
        this.meetingStorage = meetingStorage;
        this.statusStorage = statusStorage;
        this.tradeStorage = tradeStorage;
        this.storageGateway = storageGateway;
    }


    /** Return the type of account that is linked with this controller
     *
     * @return The account type linked with this controller
     */
    public String getAccountType(){
        return "ADMIN";
    }




    //Fadi's Methods


    public String registerAdmin(String username, String password, String email) {
        return "SUCCESS";
    }

    //Update status
    public List<List<String>> showFreezeUsers(int borrowThreshold, int incompleteThreshold, int weeklyThreshold) throws InvalidStatusTypeException, IOException {
        List<String> usernames = accountStorage.getUsernames();
        for(String username:usernames){
            if(!statusStorage.containsStatus(username,"FROZEN")){
            statusStorage.createStatus(username,"FROZEN");
            }
        }
        storageGateway.saveStorageData(StorageEnum.STATUS);
        return tradeStorage.showFreezeUsers(usernames, borrowThreshold, incompleteThreshold, weeklyThreshold);
    }

    public List<String> getGildedUsers() throws TradeNumberException, InvalidStatusTypeException, IOException {
        List<String> usernames = accountStorage.getUsernames();
        for(String username:usernames){
            if(!statusStorage.containsStatus(username,"GILDED")){
                statusStorage.createStatus(username,"GILDED");
            }
        }
        storageGateway.saveStorageData(StorageEnum.STATUS);
        return tradeStorage.getGildedUsers(usernames);
    }

    public List<HashMap<String, String>> showItemRequests() throws ItemNotFoundException {
        List<HashMap<String, String>> unverifiedItems = itemStorage.getUnverifiedItemsData();
        for (HashMap<String, String> itemData : unverifiedItems) {
            itemData.remove("who have add it to wishlist");
            itemData.remove("isVerified");
        }
        return unverifiedItems;
    }




    //Warren's Methods


    //Status
    public void createStatus(String username, String type) throws InvalidStatusTypeException {
        statusStorage.createStatus(username, type);
    }


    public void removeStatus(String username, String type) throws StatusNotFoundException {
        statusStorage.removeStatus(username, type);
    }


    public boolean containsStatus(String username, String type) {
        return statusStorage.containsStatus(username, type);
    }


    public List<String> getAccountStatusStrings(String username) {
        return statusStorage.getAccountStatusStrings(username);
    }


    //Item Methods
    public void newItem(String owner, String name, String description, List<String> tags)
            throws AccountNotFoundException {
        if(!accountStorage.isUsernameInUse(owner)){
            throw new AccountNotFoundException();
        }
        itemStorage.newItem(owner, name, description, tags);
    }


    public void removeItem(int itemId) throws ItemNotFoundException{
        itemStorage.removeItem(itemId);
    }


    public void verifyItem(int itemId) throws ItemNotFoundException {
        itemStorage.verifyItem(itemId);
    }












    //TODO: Add a method that changes the item's owner



    //TODO: Trade and Meeting Methods (i.e., accessing information regarding the two)
}