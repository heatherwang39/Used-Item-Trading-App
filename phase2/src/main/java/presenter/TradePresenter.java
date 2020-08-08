package main.java.presenter;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.*;

/**
 * Presenter for formatting Trade data into an easy to output GUI format
 * Useful for Offers, Activity tabs
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class TradePresenter {
    private final ItemStorage itemStorage;

    /**
     * Initializes a new TradePresenter
     *
     * @param storageGateway gateway for loading and saving information
     */
    public TradePresenter(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        StorageFactory sf = new StorageFactory();
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    /**
     * Formats a list of HashMaps representing data for Trade objects for GUI Lists to display information about the Trades
     * Format of trade: "TYPE, Traders: user1, user2, ..., Items: item1, item2, ..."
     * Example: "CYCLE, Traders: Fadi, Warren, Items: book, shirt"
     *
     * @param tradesData List of HashMaps where each HashMap is the data of a Trade
     * @return List of formatted Strings of each Trade object in tradesData
     * @throws ItemNotFoundException Thrown if there is an item not in the system
     */
    public List<String> formatTradeForListView(List<HashMap<String, List<String>>> tradesData) throws ItemNotFoundException {
        List<String> formatTrade = new ArrayList<>();
        for (HashMap<String, List<String>> tradeData : tradesData) {
            StringBuilder tradeInfo = new StringBuilder(tradeData.get("type").get(0));
            tradeInfo.append(", Traders: ");
            for (String username : tradeData.get("traders")) {
                tradeInfo.append(username);
                tradeInfo.append(", ");
            }
            tradeInfo.append("Items: ");
            List<String> itemNames = itemStorage.showNames(getIntegerList(tradeData.get("items original")));
            for (String itemName : itemNames){
                tradeInfo.append(itemName);
                tradeInfo.append(", ");
            }
            tradeInfo.deleteCharAt(tradeInfo.length() - 1);
            formatTrade.add(tradeInfo.toString());
        }
        return formatTrade;
    }

    /**
     * Formats a list of lists of item IDs that were part of a trade for GUI Lists to display information about the Items
     * Format of items: "Name1 owned by Owner1 traded for Name2 owned by Owner2 ..."
     * Example: "Shirt owned by Warren traded for Book owned by Fadi"
     *
     * @param itemIDSets List of trades represented by lists of the IDs of items that were part of the Trade
     * @return List of formatted Strings of each item trade data
     * @throws ItemNotFoundException Thrown if there is an item not in the system
     */
    public List<String> formatItemsInTradeForListView(List<List<Integer>> itemIDSets) throws ItemNotFoundException {
        List<String> formatItem = new ArrayList<>();
        for (List<Integer> itemIDSet : itemIDSets) {
            StringBuilder itemInfo = new StringBuilder();
            for (Integer itemID : itemIDSet) {
                HashMap<String, String> itemData = itemStorage.getData(itemID);
                if (!(itemInfo.length() == 0))itemInfo.append( " traded for ");
                itemInfo.append(itemData.get("name"));
                itemInfo.append(" owned by ");
                itemInfo.append(itemData.get("owner"));
            }
            formatItem.add(itemInfo.toString());
        }
        return formatItem;
    }

    private List<Integer> getIntegerList(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        for (String string : stringList) {
            integerList.add(Integer.parseInt(string));
        }
        return integerList;
    }
}
