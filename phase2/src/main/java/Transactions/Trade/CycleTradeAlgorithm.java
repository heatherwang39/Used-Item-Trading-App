package main.java.Transactions.Trade;

import java.util.ArrayList;
import java.util.List;

public class CycleTradeAlgorithm implements TradeAlgorithm {

    public List<Integer> getExchangedItems(List<Integer> originalItems){
        int max_i = originalItems.size();
        List<Integer> exchange = new ArrayList();
        int i = 0;
        while(i < max_i){
            if(i == max_i - 1){
                exchange.add(originalItems.get(0));
            }
            else{
            exchange.add(originalItems.get(i + 1));
            }
        }
        return exchange;
    }

    public String getTradeAlgorithmName() {
        return "CYCLE";
    }
}
