package main.java.controller;

import main.java.model.item.ItemStorage;
import main.java.model.trade.*;
import main.java.presenter.TradePresenter;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Offers tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class OffersController {
    private final StorageGateway storageGateway;
    private final TradeStorage tradeStorage;
    private final String username;

    /**
     * Initializes a new OffersController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the Offers tab
     * @throws IOException file cannot be read/written
     * @throws ClassNotFoundException serialized class not found
     */
    public OffersController(StorageGateway storageGateway, String username, TradePresenter tradePresenter) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;

        StorageFactory sf = new StorageFactory();
        tradeStorage = (TradeStorage) sf.getStorage(storageGateway, StorageEnum.TRADE);
        ItemStorage itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.ITEM);
        tradeStorage.attachTradeObserver(itemStorage);
    }

    /**
     * Gets a list containing HashMaps of data of all Trades that the user has been offered
     *
     * @return List of Trade data
     * @throws TradeNumberException An invalid trade was found
     */
    public List<HashMap<String, List<String>>> getOffers() throws TradeNumberException {
        return tradeStorage.getTradesData(tradeStorage.getInactiveUserOffers(username));
    }

    /**
     * Accepts the Trade offer with given tradeNumber
     *
     * @param tradeNumber id of Trade being accepted
     * @throws TradeNumberException invalid tradeNumber, not in system
     * @throws TradeCancelledException if the given trade has already been cancelled
     * @throws WrongTradeAccountException if the given trade does not involve the user
     * @throws IOException file cannot be read/written
     */
    public void acceptOffer(int tradeNumber) throws TradeNumberException, IOException, TradeCancelledException, WrongTradeAccountException {
        tradeStorage.acceptTrade(tradeNumber, username);
        storageGateway.saveStorageData(StorageEnum.TRADE);
        storageGateway.saveStorageData(StorageEnum.ITEM);
    }

    /**
     * Rejects the Trade offer with given tradeNumber
     *
     * @param tradeNumber id of Trade being rejected
     * @throws TradeNumberException invalid tradeNumber, not in system
     * @throws IOException file cannot be read/written
     */
    public void rejectOffer(int tradeNumber) throws TradeNumberException, IOException{
        tradeStorage.setStatus(tradeNumber, -1);
        storageGateway.saveStorageData(StorageEnum.valueOf("TRADE"));
    }


}
