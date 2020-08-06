package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.model.trade.NoSuchTradeAlgorithmException;
import main.java.model.trade.TradeAlgorithmName;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;
import java.util.*;

import java.io.IOException;

/**
 * Controller that returns the needed information for GUI client to display for Request tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class RequestController {
    private StorageGateway storageGateway;
    private TradeStorage tradeStorage;
    private ItemStorage itemStorage;
    private String username;

    public RequestController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("TRADE"));
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    public void createRequest(boolean permanent, TradeAlgorithmName tradeAlgorithmName, int itemRequest, int itemOffer)
            throws ItemNotFoundException, NoSuchTradeAlgorithmException, IOException {
        List<String> traders = new ArrayList<>();
        List<Integer> items = new ArrayList<>();
        traders.add(username);
        traders.add(itemStorage.getData(itemRequest).get("owner"));
        items.add(itemRequest);
        items.add(itemOffer);
        tradeStorage.newTrade(permanent, tradeAlgorithmName, traders, items);
        storageGateway.saveStorageData(StorageEnum.valueOf("TRADE"));
    }

}
