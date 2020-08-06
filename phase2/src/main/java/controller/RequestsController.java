package main.java.controller;

import main.java.model.status.StatusNotFoundException;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;
import java.util.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * Controller that returns the needed information for GUI client to display for Requests tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class RequestsController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final String username;

    /**
     * Initializes a new RequestsController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the requests tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public RequestsController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("TRADE"));
    }

    /**
     * Gets a list containing HashMaps of data of all Trades that the user has requested
     *
     * @return List of Trade datas
     * @throws TradeNumberException An invalid trade was found
     */
    public List<HashMap<String, List<String>>> getRequests() throws TradeNumberException {
        return tradeStorage.getTradesData(tradeStorage.getInactiveUserRequests(username));
    }

    /**
     * Cancels the Trade request with given tradeNumber
     *
     * @param tradeNumber id of Trade being cancelled
     * @throws TradeNumberException invalid tradeNumber, not in system
     * @throws IOException
     */
    public void cancelRequest(int tradeNumber) throws TradeNumberException, IOException, StatusNotFoundException {
        tradeStorage.setStatus(tradeNumber, -1);
        storageGateway.saveStorageData(StorageEnum.valueOf("TRADE"));
    }
}
