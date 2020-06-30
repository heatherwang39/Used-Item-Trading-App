package main.java;

import java.time.LocalDateTime;
import java.util.List;

public class TradeManager {
    private List<Trade> trades;
    

    //TODO: Write the JavaDoc for this Method
    public int newOneWayTrade(Boolean permanent, String sender, String receiver, int item){
        int t;
        if(permanent){t = newOWPTrade(sender, receiver, item);}
        else{t = newOWTTrade(sender, receiver, item);}
        return t;
    }

    private int newOWPTrade(String sender, String receiver, int item){
        Trade t = new OneWayPermanentTrade(sender, receiver, item);
        trades.add(t);
        return t.getTradeNumber();
    }

    private int newOWTTrade(String sender, String receiver, int item){
        Trade t = new OneWayTemporaryTrade(sender, receiver, item);
        trades.add(t);
        return t.getTradeNumber();
    }


    //TODO: Write the JavaDoc for this Method
    public int newTwoWayTrade(Boolean permanent, String firstTrader, int firstItem,
                               String secondTrader, int secondItem){
        int t;
        if(permanent){t = newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);}
        else{t = newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);}
        return t;
    }

    private int newTWPTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayPermanentTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        return t.getTradeNumber();
    }

    private int newTWTTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayTemporaryTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        return t.getTradeNumber();
    }


    //TODO:Write the JavaDoc for this Method
    public Trade getTrade(int tradeNumber) throws TradeNumberException{
        for(Trade t:trades){
            if(t.getTradeNumber() == tradeNumber){
                return t;
            }
        }
        throw new TradeNumberException();
    }
}