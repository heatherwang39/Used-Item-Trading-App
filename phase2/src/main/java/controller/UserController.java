package main.java.controller;


import main.java.model.account.AccountStorage;
import main.java.model.item.Item;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeStorage;

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
    private AccountStorage accountStorage;
    private ItemStorage itemStorage;
    private MeetingStorage meetingStorage;
    private StatusStorage statusStorage;
    private TradeStorage tradeStorage;



    public UserController(String username, AccountStorage accountStorage, ItemStorage itemStorage,
                           MessageStorage messageStorage,MeetingStorage meetingStorage, StatusStorage statusStorage,
                           TradeStorage tradeStorage){

        super(username, messageStorage, itemStorage, accountStorage);

        this.accountStorage = accountStorage;
        this.itemStorage = itemStorage;
        this.meetingStorage = meetingStorage;
        this.statusStorage = statusStorage;
        this.tradeStorage = tradeStorage;
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

    public List<HashMap<String, String>> getVerifiedInventoryData(){
        return itemStorage.getVerifiedInventoryData(username);
    }


    public List<HashMap<String, String>> getWishlistData(){
        return itemStorage.getWishlistData(username);
    }


    public List<HashMap<String, String>> suggestItems(String givenUser){
        return itemStorage.suggestItems(username, givenUser);
    }


    public void newItem(String name, String description, List<String> tags) {
        itemStorage.newItem(username, name, description, tags);
    }







    //TODO: Trade and Meeting Methods (i.e., accessing information regarding the two)
}
