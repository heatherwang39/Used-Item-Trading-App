package main.java.controller;

import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;

import javax.swing.*;
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
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the Activity tab
     * @throws IOException file can't be read/written
     * @throws ClassNotFoundException serialized class not found
     */
    public ActivityController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.username = username;
        StorageDepot sd = new StorageDepot(storageGateway);
        tradeStorage = sd.getTradeStorage();
    }

    /**
     * Returns a list of at most the three most recent trades a user with given username has participated in
     *
     * @return List containing lists with the ids of the items that were a part of the most recent trades
     * @throws TradeNumberException an invalid trade number is found
     */
    public List<List<Integer>> recentItemsTraded() throws TradeNumberException {
        return tradeStorage.recentItemsTraded(username);
    }

    /**
     * Returns a list of at most the three most frequent trading partners for the user with given username
     *
     * @return list of the usernames of the most frequent trading partners
     * @throws TradeNumberException an invalid trade number is found
     */
    public List<String> frequentTradingPartners() throws TradeNumberException {
        return tradeStorage.frequentTradePartners(username);
    }
}
