package main.java.Transactions.Trade;

import main.java.Transactions.WrongAccountException;

import java.time.LocalDateTime;
import java.util.*;


/**
 * Use case class for initializing, storing, and retrieving information regarding Trades.
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 1
 */

public class TradeStorage implements Observer {
    private List<Trade> trades;
    private FreezeManager fm = new FreezeManager();
    private ActivityManager am = new ActivityManager();


    public TradeStorage(List<Trade> trades){
        this.trades = trades;
    }




    //Trade Constructor below


    /** Initializes a new Trade based on the given parameters. Return the tradeNumber of the newly initialized Trade.
     *
     * @param permanent Whether the trade is permanent or not
     * @param tradeAlgorithmName The name of the Trade Algorithm that is associated with this trade
     * @param traders The traders involved in this trade
     * @param items The items in the order of their original owners (this depends on traders)
     * @return The Trade Number of the newly initialized Trade.
     * @throws NoSuchTradeAlgorithmException Thrown if no tradeAlgorithm has the given name
     */
    public int newTrade(boolean permanent, TradeAlgorithmName tradeAlgorithmName, List<String> traders, List<Integer> items)
            throws NoSuchTradeAlgorithmException{

        Trade t;

        TradeAlgorithm ta;
        switch(tradeAlgorithmName){
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


    /**
     * Return the Status of the Trade corresponding to the given tradeNumber
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade has been accepted but no items have been exchanged yet
     * 2 represents that the trade is ongoing (items have been exchanged)
     * 3 represents that the trade has been completed
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get the Status of
     * @return The Status of the Trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public int getStatus(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getStatus();
    }


    /**
     * Changes the status of the Trade object. Iff the change was successfully made, return True.
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade has been accepted but no items have been exchanged yet
     * 2 represents that the trade is ongoing (items have been exchanged)
     * 3 represents that the trade has been completed
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to set the Status of
     * @param status The new status of the Trade
     * @return A boolean representing whether or not the change was made
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public boolean setStatus(int tradeNumber, int status) throws TradeNumberException{
        return getTrade(tradeNumber).setStatus(status);
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
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


    public void setMeetings(int tradeNumber, List<Integer> meetings)
            throws TradeNumberException, TradeCancelledException, MaxNumMeetingsExceededException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        if(meetings.size() > t.getTotalNumMeetings()){throw new MaxNumMeetingsExceededException();}
        t.setMeetings(meetings);
    }

    public void addMeeting(int tradeNumber, int meetingID)
            throws TradeNumberException, TradeCancelledException, MaxNumMeetingsExceededException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        if(t.getCurrNumMeetings() >= t.getTotalNumMeetings()){throw new MaxNumMeetingsExceededException();}
        t.addMeeting(meetingID);
    }


    public void removeLastMeeting(int tradeNumber) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.removeLastMeeting();
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


    //Search Methods Below

    public int getTradeWithMeeting(int meetingID) throws NoTradeWithMeetingIDException{
        for (Trade t : trades) {
            if (t.getMeetings().contains(meetingID)) {
                return t.getTradeNumber();
            }
        }
        throw new NoTradeWithMeetingIDException();
    }


    public List<Integer> getTradesWithUser(String username){
        List<Integer> userTrades = new ArrayList();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                userTrades.add(t.getTradeNumber());
            }
        }
        return userTrades;
    }


    public List<Integer> getActiveTradesWithUser(String username){
        List<Integer> activeUserTrades = new ArrayList();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                if(t.getStatus() == 1 | t.getStatus() == 2){
                    activeUserTrades.add(t.getTradeNumber());
                }
            }
        }
        return activeUserTrades;
    }


    public List<Integer> getTradesWithItem(int itemID){
        List<Integer> tradesWithItems = new ArrayList();
        for (Trade t : trades) {
            if (t.getTraders().contains(itemID)) {
                tradesWithItems.add(t.getTradeNumber());
            }
        }
        return tradesWithItems;
    }

    /** Return the Trade ID of the active trade that contains the given item. If no Trade is both active and contains
     * the given item, return 0.
     *
     * @param itemID The ID of the item you're looking for
     * @return The ID of the active trade with the item, or 0 if no trade contains the item
     */
    public int getActiveTradeWithItem(int itemID){
        for (Trade t : trades) {
            if (t.getTraders().contains(itemID)) {
                if(t.getStatus() == 1 | t.getStatus() == 1){return t.getTradeNumber();}
            }
        }
        return 0;
    }


    public List<Integer> getActiveTrades(){
        List<Integer> activeTrades = new ArrayList();
        for (Trade t : trades) {
            if(t.getStatus() == 1 | t.getStatus() == 2){
                activeTrades.add(t.getTradeNumber());
            }
        }
        return activeTrades;
    }


    public List<Integer> getCompletedTrades(){
        List<Integer> completedTrades = new ArrayList();
        for (Trade t : trades) {
            if(t.getStatus() == 3){
                completedTrades.add(t.getTradeNumber());
            }
        }
        return completedTrades;
    }





    //Observer Pattern Below
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String){
            String message = (String) arg;
            if(message.charAt(0) == 'A'){
                int i = Integer.parseInt(message.substring(1));

                try{
                    resetWarnings(getTradeWithMeeting(i));
                }
                catch(TradeNumberException e){}
                catch(NoTradeWithMeetingIDException e){}
            }
            else if(message.charAt(0) == 'C'){
                int i = Integer.parseInt(message.substring(1));

                try{
                    Trade t = getTrade(getTradeWithMeeting(i));
                    if(t.getCurrNumMeetings() == t.getTotalNumMeetings()){
                        t.setStatus(3);
                    }
                }
                catch(TradeNumberException e){}
                catch(NoTradeWithMeetingIDException e){}
            }
        }
    }




    //Other Methods


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