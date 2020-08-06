package main.java.controller;


import main.java.model.account.AccountStorage;
import main.java.model.item.Item;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for specifically User account
 *
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class UserController extends LoginAccountController {
    //private AccountStorage accountStorage;  ?already inherit from super class
    //private ItemStorage itemStorage;        ?already inherit from super class
    private final MeetingStorage meetingStorage;
    //private StatusStorage statusStorage;    ?already inherit from super class
    private final TradeStorage tradeStorage;



    public UserController(String username, StorageGateway storageGateway) throws IOException, ClassNotFoundException {

        super(username, storageGateway);
        StorageFactory storageFactory = new StorageFactory();
        this.meetingStorage = (MeetingStorage)storageFactory.getStorage(storageGateway, StorageEnum.MEETING);
        this.tradeStorage = (TradeStorage)storageFactory.getStorage(storageGateway, StorageEnum.TRADE);
    }


    /** Return the type of account that is linked with this controller
     *
     * @return The account type linked with this controller
     */
    public String getAccountType(){
        return "USER";
    }



    //User's Status Method

    public List<String> getAccountStatusStrings() {
        return statusStorage.getAccountStatusStrings(username);
    }


    //User's Items Method

    public List<HashMap<String, String>> getVerifiedInventoryData() throws ItemNotFoundException {
        return itemStorage.getVerifiedInventoryData(username);
    }


    public List<HashMap<String, String>> getWishlistData() throws ItemNotFoundException {
        return itemStorage.getWishlistData(username);
    }


    public List<HashMap<String, String>> suggestItems(String givenUser) throws ItemNotFoundException {
        return itemStorage.suggestItems(username, givenUser);
    }


    public void newItem(String name, String description, List<String> tags) {
        itemStorage.newItem(username, name, description, tags);
    }







    //TODO: Trade and Meeting Methods (i.e., accessing information regarding the two)
}
