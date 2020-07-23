package main.java.Transactions.Trade;

import java.util.List;

public class TemporaryTrade extends Trade {


    public TemporaryTrade(int tradeNumber, TradeAlgorithm tradeAlgorithm, List<String> traders, List<Integer> items){
        super(tradeNumber, tradeAlgorithm, traders, items);
    }


    public boolean isPermanent(){
        return false;
    }


    public List<Integer> getItemsFinal(){
        return getItemsOriginal();
    }
}
