package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeStorage;

import java.util.HashMap;
import java.util.List;


/**
 * Controller that returns the needed information for GUI client to display for specifically admin account
 *
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class AdminController implements AccountController {
    private AccountStorage accountStorage;
    private ItemStorage itemStorage;
    private MessageStorage messageStorage;
    private MeetingStorage meetingStorage;
    private StatusStorage statusStorage;
    private TradeStorage tradeStorage;









    /** Return the type of account that is linked with this controller
     *
     * @return The account type linked with this controller
     */
    public String getAccountType(){
        return "ADMIN";
    }









    public String registerAdmin(String username, String password, String email) {
        try {
            accountStorage.createAdmin(username, password, email);
        } catch (InvalidLoginException | InvalidEmailAddressException | UsernameInUseException |
                EmailAddressInUseException e) {
            return e.getMessage();
        }
        return "SUCCESS";
    }



    public List<List<String>> showFreezeUsers(int borrowThreshold, int incompleteThreshold, int weeklyThreshold) {
        List<String> usernames = accountStorage.getUsernames();
        return tradeStorage.showFreezeUsers(usernames, borrowThreshold, incompleteThreshold, weeklyThreshold);
    }





    public List<HashMap<String, String>> showItemRequests() {
        List<HashMap<String, String>> unverifiedItems = itemStorage.getUnverifiedItemsData();
        for (HashMap<String, String> itemData : unverifiedItems) {
            itemData.remove("who have add it to wishlist");
            itemData.remove("isVerified");
        }
        return unverifiedItems;
    }
}