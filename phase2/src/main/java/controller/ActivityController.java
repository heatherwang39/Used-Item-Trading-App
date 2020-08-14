package main.java.controller;

import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system.StorageDepot;
import java.util.*;

import java.io.IOException;

/**
 * Controller that returns the needed information for GUI client to display for Activity tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class ActivityController {
    private final TradeStorage tradeStorage;
    private final String username;

    /**
     * Initializes a new ActivityController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     * @param username username of the user accessing the Activity tab
     */
    public ActivityController(StorageDepot storageDepot, String username) {
        this.username = username;
        tradeStorage = storageDepot.getTradeStorage();
    }

    /**
     * Returns a list of at most the three most recent trades a user with given username has participated in
     *
     * @return List containing lists with the ids of the items that were a part of the most recent trades
     */
    public List<List<Integer>> recentItemsTraded() {
        return tradeStorage.recentItemsTraded(username);
    }

    /**
     * Returns a list of at most the three most frequent trading partners for the user with given username
     *
     * @return list of the usernames of the most frequent trading partners
     */
    public List<String> frequentTradingPartners() {
        return tradeStorage.frequentTradePartners(username);
    }
}
