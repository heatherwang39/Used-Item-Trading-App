package main.java.controller;

import main.java.model.item.ItemStorage;
import main.java.model.trade.TradeNumberException;
import main.java.model.trade.TradeStorage;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BrowseController {
    private final StorageGateway storageGateway;
    private ItemStorage itemStorage;

    public BrowseController(StorageGateway storageGateway, String username, StorageGateway storageGateway1) throws IOException {
        this.storageGateway = storageGateway;
        itemStorage = new ItemStorage(storageGateway);

    }
}
