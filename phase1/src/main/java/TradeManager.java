package main.java;

import java.util.List;

public class TradeManager {
    private int numTrades = 0;
    private List<Trade> trades;

    //TODO: Create a public method that creates TwoPersonMeetings and moves it into a Trade


    //TODO: Write the JavaDoc for this Method
    public void newOneWayTrade(Boolean permanent, UserAccount sender, UserAccount receiver, Item item){
        if(permanent){newOWPTrade(sender, receiver, item);}
        else{newOWTTrade(sender, receiver, item);}
    }

    private void newOWPTrade(UserAccount sender, UserAccount receiver, Item item){
        Trade t = new OneWayPermanentTrade(numTrades + 1, sender, receiver, item);
        numTrades++;
        trades.add(t);
    }

    private void newOWTTrade(UserAccount sender, UserAccount receiver, Item item){
        Trade t = new OneWayTemporaryTrade(numTrades + 1, sender, receiver, item);
        numTrades++;
        trades.add(t);
    }


    //TODO: Write the JavaDoc for this Method
    public void newTwoWayTrade(Boolean permanent, UserAccount firstTrader, Item firstItem,
                               UserAccount secondTrader, Item secondItem){
        if(permanent){newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);}
        else{newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);}
    }

    private void newTWPTrade(UserAccount firstTrader, Item firstItem, UserAccount secondTrader, Item secondItem){
        Trade t = new TwoWayPermanentTrade(numTrades + 1, firstTrader, firstItem, secondTrader, secondItem);
        numTrades++;
        trades.add(t);
    }

    private void newTWTTrade(UserAccount firstTrader, Item firstItem, UserAccount secondTrader, Item secondItem){
        Trade t = new TwoWayTemporaryTrade(numTrades + 1, firstTrader, firstItem, secondTrader, secondItem);
        numTrades++;
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