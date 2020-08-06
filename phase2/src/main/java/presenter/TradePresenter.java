package main.java.presenter;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.*;

public class TradePresenter {
    private ItemStorage itemStorage;

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

    private List<Integer> getIntegerList(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        for (String string : stringList) {
            integerList.add(Integer.parseInt(string));
        }
        return integerList;
    }
}
