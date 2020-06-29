package main.java;

import java.util.List;

public class TradeManager {
    private List<Trade> trades;

    //TODO: Create a public method that creates TwoPersonMeetings and moves it into a Trade


    //TODO: Write the JavaDoc for this Method
    public void newOneWayTrade(Boolean permanent, String sender, String receiver, int item){
        if(permanent){newOWPTrade(sender, receiver, item);}
        else{newOWTTrade(sender, receiver, item);}
    }

    private void newOWPTrade(String sender, String receiver, int item){
        Trade t = new OneWayPermanentTrade(sender, receiver, item);
        trades.add(t);
    }

    private void newOWTTrade(String sender, String receiver, int item){
        Trade t = new OneWayTemporaryTrade(sender, receiver, item);
        trades.add(t);
    }


    //TODO: Write the JavaDoc for this Method
    public void newTwoWayTrade(Boolean permanent, String firstTrader, int firstItem,
                               String secondTrader, int secondItem){
        if(permanent){newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);}
        else{newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);}
    }

    private void newTWPTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayPermanentTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
    }

    private void newTWTTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayTemporaryTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
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