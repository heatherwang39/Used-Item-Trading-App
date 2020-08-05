package main.java.system;

import main.java.model.account.*;
import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.trade.MaxNumMeetingsExceededException;
import main.java.model.trade.TradeCancelledException;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Presenter that returns the needed information for GUI client to display for specifically user accounts
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public class UserSystem extends LoginSystem implements UserInterface, Registerable, Meetable, Updatable, Viewable {

    public UserSystem(ItemStorage is, TradeStorage ts, AccountStorage as, String username){super(is, ts, as, username);}

    @Override
    public String register(String username, String password, String email) {
        return "SUCCESS";
    }

    @Override
    public List<HashMap<String, String>> viewInventory() {return is.getVerifiedInventoryData(username);}

    @Override
    public List<HashMap<String, String>> viewWishlist() {return is.getWishlistData(username);}

    @Override
    public boolean createRequest(int itemId) {
        return false;
    } //TODO: implement

    @Override
    public String setMeeting(int tradeNumber, List<Integer> meetings) {
        try {
            ts.setMeetings(tradeNumber, meetings);
            return "SUCCESS";
        } catch (TradeNumberException | TradeCancelledException | MaxNumMeetingsExceededException e) {
            return e.getMessage();
        }
    }

    @Override
    public void addItem(String name, String description, List<String> tags) {
        is.newItem(username, name, description, tags);
    }

    @Override
    public void addWishlist(int itemId) {
        //Needs a method for adding to wishlist in item storage
    }

    @Override
    public List<String> showFrequentPartners() {
        try {
            return ts.frequentTradePartners(username);
        } catch (TradeNumberException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<List<String>> showRecentItems() {
        List<List<String>> recentItems = new ArrayList<>();
        try {
            List<List<Integer>> recentItemIds = ts.recentItemsTraded(username);
            for (List<Integer> itemIds : recentItemIds) {
                List<String> items = is.showNames(itemIds);
                items.add(items.get(0));
                if (itemIds.size() == 2 && !(itemIds.get(1) == null)) items.add(items.get(1));
                recentItems.add(items);
            }
            return recentItems;
        } catch (TradeNumberException | ItemNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<List<String>> showOffers() {
        List<List<String>> offers = new ArrayList<>();
        List<Integer> inactiveUserTrades = ts.getInactiveTradesWithUser(username);
        for (Integer tradeId : inactiveUserTrades) {
            try {
                if (ts.getTraders(tradeId).get(1).equals(username)) offers.add(getTradeData(tradeId));
            } catch (TradeNumberException | ItemNotFoundException e) {
                break;
            }
        }
        return offers;
    }

    @Override
    public List<List<String>> showActiveTrades() {
        List<Integer> activeTradeIds = ts.getActiveTradesWithUser(username);
        List<List<String>> activeTrades = new ArrayList<>();
        for (Integer tradeId : activeTradeIds) {
            try {
                activeTrades.add(getTradeData(tradeId));
            } catch (TradeNumberException | ItemNotFoundException e) {
                break;
            }
        }
        return activeTrades;
    }

    private List<String> getTradeData(Integer tradeId) throws TradeNumberException, ItemNotFoundException{
        List<String> trade = new ArrayList<>();
        List<Integer> itemsOriginal = ts.getItemsOriginal(tradeId);
        trade.add(ts.getTraders(tradeId).get(0)); //Adds the name of the user making the offer
        trade.addAll(is.showNames(itemsOriginal)); //adds the name of the items in the trade
        return trade;
    }
}
