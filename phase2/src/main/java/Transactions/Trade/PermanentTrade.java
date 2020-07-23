package main.java.Transactions.Trade;

import java.util.List;

public class PermanentTrade extends Trade {


    public PermanentTrade(int tradeNumber, TradeAlgorithm tradeAlgorithm, List<String> traders, List<Integer> items){
        super(tradeNumber, tradeAlgorithm, traders, items);
    }


    public boolean isPermanent(){
        return true;
    }


    public List<Integer> getItemsFinal(){
        return getItemsExchanged();
    }
}
