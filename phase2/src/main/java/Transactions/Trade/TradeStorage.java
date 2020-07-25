package main.java.Transactions.Trade;

import main.java.Transactions.WrongAccountException;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Use case class for initializing, storing, and returning trades.
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 1
 */

public class TradeStorage {
    private List<Trade> trades;
    private FreezeManager fm = new FreezeManager();
    private ActivityManager am = new ActivityManager();


    public TradeStorage(List<Trade> trades){
        this.trades = trades;
    }




    //Trade Constructor below


    /** Initializes a new Trade based on the given parameters. Return the tradeNumber of the newly initialized Trade.
     *
     * @param permanent
     * @param tan
     * @param traders
     * @param items
     * @return
     * @throws NoSuchTradeAlgorithmException
     */
    public int newTrade(boolean permanent, TradeAlgorithmName tan, List<String> traders, List<Integer> items)
            throws NoSuchTradeAlgorithmException{

        Trade t;

        TradeAlgorithm ta;
        switch(tan){
            case CYCLE:
                ta = new CycleTradeAlgorithm();
                break;
            default:
                throw new NoSuchTradeAlgorithmException();
        }

        if(permanent){
            t = new PermanentTrade(getNumberOfTrades() + 1, ta, traders, items);
        } else{
            t = new TemporaryTrade(getNumberOfTrades() + 1, ta, traders, items);
        }

        return t.getTradeNumber();
    }


    //TradeStorage and helper methods below


    /**
     * Gets the total number of trades made
     *
     * @return the size of the trades List
     */
    public int getNumberOfTrades() {
        return trades.size();
    }


    //I added this method to help with freeze manager, lmk if you don't like it -Fadi
    private List<Trade> getUserTrades(String username) throws TradeNumberException {
        List<Trade> userTrades = new ArrayList<>();
        int i = 0;
        for (Trade trade : trades){
            if (getTraders(i).contains(username)) userTrades.add(trade);
            i++;
        }
        return userTrades;
    }


    private Trade getTrade(int tradeNumber) throws TradeNumberException {
        for (Trade t : trades) {
            if (t.getTradeNumber() == tradeNumber) {
                return t;
            }
        }
        throw new TradeNumberException();
    }


    private void checkTradeCancelled(Trade t) throws TradeCancelledException{
        if(t.getStatus() == -1){
            throw new TradeCancelledException();
        }
    }




    //Trade-related Methods Below


    public int getStatus(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getStatus();
    }

    public boolean setStatus(int tradeNumber, int status) throws TradeNumberException{
        return getTrade(tradeNumber).setStatus(status);
    }


    public boolean isPermanent(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).isPermanent();
    }

    public List<String> getTraders(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getTraders();
    }

    public List<Integer> getItemsOriginal(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getItemsOriginal();
    }

    public List<Integer> getItemsExchanged(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getItemsExchanged();
    }

    public List<Integer> getItemsFinal(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getItemsFinal();
    }


    public String getAlgorithmName(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getAlgorithmName();
    }


    //Methods regarding Warnings below


    public void warn(int tradeNumber) throws TradeNumberException{
        getTrade(tradeNumber).warn();
    }


    public void resetWarnings(int tradeNumber) throws TradeNumberException{
        getTrade(tradeNumber).resetWarnings();
    }


    public void setMaxWarnings(int tradeNumber, int maxWarnings) throws TradeNumberException{
        getTrade(tradeNumber).setMaxWarnings(maxWarnings);
    }


    //Meeting related Methods Below


    public void setMeetings(int tradeNumber, List<Integer> meetings) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.setMeetings(meetings);
    }

    public void addMeeting(int tradeNumber, int meetingID) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.addMeeting(meetingID);
    }

    public void removeMeeting(int tradeNumber, int meetingID) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.removeMeeting(meetingID);
    }

    public int getMeeting(int tradeNumber, int meetingNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getMeeting(meetingNumber);
    }

    public int getCurrNumMeetings(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getCurrNumMeetings();
    }

    public int getTotalNumMeetings(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getTotalNumMeetings();
    }

    public void setNumMeetings(int tradeNumber, int numMeetings) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.setNumMeetings(numMeetings);
    }




    //Methods Regarding Trade Acceptance Below


    public List<String> getUnacceptedTraders(int tradeNumber) throws TradeNumberException {
        return getTrade(tradeNumber).getUnacceptedTraders();
    }


    public void acceptTrade(int tradeNumber, String trader)
            throws TradeNumberException, TradeCancelledException, WrongAccountException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        try{t.acceptTrade(trader);}
        catch(IndexOutOfBoundsException e){throw new WrongAccountException();}
    }



























































    //TODO: Implement the Mediator Pattern between TradeStorage and MeetingStorage


    //TODO: Examine Fadi's Methods (below). They may need to change due to the dramatic changes in the Trade Class


    public List<String> checkUserShouldFreeze(String username, int borrowThreshold, int incompleteThreshold,
                                              int weeklyThreshold) throws TradeNumberException {
        List <Trade> userTrades = getUserTrades(username);
        return fm.checkUserShouldFreeze(username, userTrades, borrowThreshold, incompleteThreshold, weeklyThreshold);
    }

    public List<List<Integer>> recentItemsTraded(String username) throws TradeNumberException {
        List <Trade> userTrades = getUserTrades(username);
        return am.recentItemsTraded(userTrades);
    }

    public List<String> frequentTradePartners(String username) throws TradeNumberException {
        List <Trade> userTrades = getUserTrades(username);
        return am.frequentTradePartners(username, userTrades);
    }

    protected boolean acceptedStatus(int tradeStatus) {
        return (tradeStatus == 1 || tradeStatus == 2 || tradeStatus == 3);
    }
}