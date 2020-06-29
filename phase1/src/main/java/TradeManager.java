package main.java;

import java.util.List, Trade;

public class TradeManager {
    private List<Trade> trades;

    //TODO: Create a public method that creates TwoPersonMeetings and moves it into a Trade

    /**
     * Sets the meeting location for this trade instance.
     * @param t The trade instance we want to set the meeting for.
     */
    public void newTwoPersonMeeting(Trade t){
        OneMeeting m = new OneMeeting; // add parameters here, requires modifying OneMeeting or expanding it
        t.setMeeting(m);


    }

    //TODO: Write the JavaDoc for this Method
    public void newOneWayTrade(Boolean permanent, UserAccount sender, UserAccount receiver, Item item){
        if(permanent){newOWPTrade(sender, receiver, item);}
        else{newOWTTrade(sender, receiver, item);}
    }

    private void newOWPTrade(UserAccount sender, UserAccount receiver, Item item){
        Trade t = new OneWayPermanentTrade(sender, receiver, item);
        trades.add(t);
    }

    private void newOWTTrade(UserAccount sender, UserAccount receiver, Item item){
        Trade t = new OneWayTemporaryTrade(sender, receiver, item);
        trades.add(t);
    }


    //TODO: Write the JavaDoc for this Method
    public void newTwoWayTrade(Boolean permanent, UserAccount firstTrader, Item firstItem,
                               UserAccount secondTrader, Item secondItem){
        if(permanent){newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);}
        else{newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);}
    }

    private void newTWPTrade(UserAccount firstTrader, Item firstItem, UserAccount secondTrader, Item secondItem){
        Trade t = new TwoWayPermanentTrade(firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
    }

    private void newTWTTrade(UserAccount firstTrader, Item firstItem, UserAccount secondTrader, Item secondItem){
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