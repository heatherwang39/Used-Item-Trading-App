package main.java.systems;

import main.java.account.*;
import main.java.item.ItemStorage;
import main.java.transactions.trade.TradeNumberException;
import main.java.transactions.trade.TradeStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Presenter that returns the needed information for GUI client to display for specifically admin account
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

class AdminSystem extends LoginSystem implements AdminInterface, ModeratorInterface {

    public AdminSystem(ItemStorage is, TradeStorage ts, AccountStorage as, String username){super(is, ts, as, username);}

    @Override
    public String registerAdmin(String username, String password, String email) {
        try {
            as.createAdmin(username, password, email);
        } catch (InvalidLoginException e) {
            return "INVALID LOGIN";
        } catch (InvalidEmailAddressException e) {
            return "INVALID EMAIL";
        } catch (UsernameInUseException e) {
            return "USERNAME IN USE";
        } catch (EmailAddressInUseException e) {
            return "EMAIL IN USE";
        }
        return "SUCCESS";
    }

    @Override
    public void updateBrowseThreshold(int newThreshold) { //TODO: implement
    }

    @Override
    public void updateIncompleteThreshold(int newThreshold) { //TODO: implement

    }

    @Override
    public void updateWeeklyThreshold(int newThreshold) { //TODO: implement

    }

    @Override
    public List<List<String>> showFreezeUsers(int borrowThreshold, int incompleteThreshold, int weeklyThreshold) {
        List<String> usernames = as.getUsernames();
        List<List<String>> freezeList = new ArrayList<>();
        for (String user : usernames) {
            try {
                List<String> userFreezeReasons = ts.checkUserShouldFreeze(user, borrowThreshold, incompleteThreshold, weeklyThreshold);
                if (userFreezeReasons.size() > 1) {
                    List<String> userData = new ArrayList<>();
                    userData.add(user);
                    userData.addAll(userFreezeReasons);
                    freezeList.add(userData);
                }
            } catch (TradeNumberException e) {
                break;
            }
        }
        return freezeList;
    }

    @Override
    public Map<Integer, String> showItemRequests() {
        return new HashMap<>(); //TODO: implement
    }
}