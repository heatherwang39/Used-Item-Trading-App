package main.java.Transactions.Trade;

import java.util.List;

public interface TradeAlgorithm {

    List<Integer> getExchangedItems(List<Integer> originalItems);

    String getTradeAlgorithmName();
}