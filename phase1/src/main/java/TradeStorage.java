package main.java;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Use case class for initializing, storing, and returning trades.
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 1
 */

public class TradeStorage {
    private List<Trade> trades;
    private String path;
    private FileReadWriter frw;

    /**
     * Creates a new TradeStorage.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public TradeStorage(String filePath) throws IOException, ClassNotFoundException {
        path = filePath;
        frw = new FileReadWriter(path);
        File file = new File(path);
        if (file.exists()) {
            try {
                trades = (List<Trade>)frw.readFromFile(path);
            } catch(EOFException ignored) {}
        } else {
            file.createNewFile();
        }
    }

    /**
     * Gets the total number of trades made
     *
     * @return the size of the trades List
     */
    public int getNumberOfTrades() { return trades.size();}

    /** Initializes a new OneWayTrade based on the given parameters. Return the tradeNumber of the newly initialized
     * OneWayTrade.
     *
     * @param permanent Whether or not the Trade is to be permanent
     * @param sender The username of the Sender of the Trade
     * @param receiver The username of the Receiver of the Trade
     * @param item The ID of the item involved in the Trade
     * @return The tradeNumber of the newly initialized Trade.
     */
    public int newOneWayTrade(Boolean permanent, String sender, String receiver, int item){
        int t;
        if(permanent){t = newOWPTrade(sender, receiver, item);}
        else{t = newOWTTrade(sender, receiver, item);}
        return t;
    }

    private int newOWPTrade(String sender, String receiver, int item){
        Trade t = new OneWayPermanentTrade(getNumberOfTrades(), sender, receiver, item);
        trades.add(t);
        return t.getTradeNumber();
    }

    private int newOWTTrade(String sender, String receiver, int item){
        Trade t = new OneWayTemporaryTrade(getNumberOfTrades(), sender, receiver, item);
        trades.add(t);
        return t.getTradeNumber();
    }


    /** Initializes a new TwoWayTrade based on the given parameters. Return the tradeNumber of the newly initialized
     * TwoWayTrade
     *
     * @param permanent Whether or not the Trade is to be permanent
     * @param firstTrader The username of the first trader.
     * @param firstItem The ID of the item the first trader sent in this trade
     * @param secondTrader The username of the second trader.
     * @param secondItem The ID of the item the second trader sent in this trade
     * @return The tradeNumber of the newly initialized Trade.
     */
    public int newTwoWayTrade(Boolean permanent, String firstTrader, int firstItem,
                               String secondTrader, int secondItem){
        int t;
        if(permanent){t = newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);}
        else{t = newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);}
        return t;
    }

    private int newTWPTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayPermanentTrade(getNumberOfTrades(), firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        return t.getTradeNumber();
    }

    private int newTWTTrade(String firstTrader, int firstItem, String secondTrader, int secondItem){
        Trade t = new TwoWayTemporaryTrade(getNumberOfTrades(), firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        return t.getTradeNumber();
    }


    /** Returns the Trade that corresponds to the given tradeNumber
     *
     * @param tradeNumber The tradeNumber of the Trade that is to be returned
     * @return The Trade that corresponds to the given tradeNumber
     * @throws TradeNumberException If no Trade with the given tradeNumber can be found
     */
    public Trade getTrade(int tradeNumber) throws TradeNumberException{
        for(Trade t:trades){
            if(t.getTradeNumber() == tradeNumber){
                return t;
            }
        }
        throw new TradeNumberException();
    }

    public List<Integer> getItemsInTrade(int tradeNumber) throws TradeNumberException{
        for(Trade t:trades){
            if(t.getTradeNumber() == tradeNumber){
                return t.getItemsOriginal();
            }
        }
        throw new TradeNumberException();
    }

    /**
     * Returns whether the trade with the corresponding trade number is currently ongoing
     * @param tradeNumber the tradeNumber of the Trade that is to be checked
     * @return true if trade is on going, false if it is not
     * @throws TradeNumberException if no Trade with the given tradeNumber can be found
     */
    public boolean checkActiveTrade(int tradeNumber) throws TradeNumberException {
        Trade trade = trades.get(tradeNumber);
        return trade.getStatus() == 1;
    }

    /**
     * Returns whether the user with given username should be frozen based on if they borrowed more than they have lent.
     * @param username username of the user
     * @return true if user should be frozen, false if not
     */
    public boolean checkUserShouldFreeze(String username, int tradeThreshold) {
        int freezeScore = 0;
        for (OneWayTrade oneWayTrade : getOneWayTrades()){
            if (oneWayTrade.getSender().equals(username)) freezeScore--;
            else if (oneWayTrade.getReceiver().equals(username)) freezeScore++;
        }
        return freezeScore > tradeThreshold;
    }

    private List<OneWayTrade> getOneWayTrades() {
        List<OneWayTrade> oneWayTrades = new ArrayList<>();
        for (Trade trade : trades){
            if (trade.isOneWay() && checkOngoingComplete(trade)) oneWayTrades.add((OneWayTrade) trade);
        }
        return oneWayTrades;
    }

    private boolean checkOngoingComplete(Trade trade) {return (trade.getStatus() == 1 || trade.getStatus() == 2); }

    public boolean checkUserWeeklyTrades(String username, int weeklyThreshold, LocalDateTime dateTime) {
        int numTradesInWeek = 0;
        try {
            for (Trade trade : trades) {
                if (trade.getTraders().contains(username) && trade.getMeetingTime(1).isAfter(dateTime)) {
                    numTradesInWeek++;
                }
            }
        } catch (MeetingNumberException e) {
            ; //TODO: I don't know what to do here
        }
        return numTradesInWeek > weeklyThreshold;
    }
}