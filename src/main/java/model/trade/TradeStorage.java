package main.java.model.trade;

import main.java.model.Storage;
import main.java.model.meeting.MeetingObserver;

import java.util.*;


/**
 * Use case class for initializing, storing, and retrieving information regarding Trades.
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 1
 */

public class TradeStorage implements Storage, MeetingObserver, TradeObservee {
    private List<Trade> trades;
    private final TradeAlgorithmCreator tac = new TradeAlgorithmFactory();
    private final TradeActivityManager tam = new TradeActivityManager();

    private final List<TradeObserver> observers = new ArrayList<>();

    @Override
    public Object getNewStorageData() {
        return new ArrayList<Trade>();
    }

    @Override
    public void setStorageData(Object trades) {
        this.trades = (List<Trade>) trades;
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
            throws NoSuchTradeAlgorithmException, ItemAlreadyInActiveTradeException{

        Trade t;

        for(Integer i: items){
            if(i != null && getActiveTradeWithItem(i) != 0){
                throw new ItemAlreadyInActiveTradeException();
            }
        }

        TradeAlgorithm ta = tac.getTradeAlgorithm(tradeAlgorithmName);

        if(permanent){
            t = new PermanentTrade(getNumberOfTrades() + 1, ta, traders, items);
        } else{
            t = new TemporaryTrade(getNumberOfTrades() + 1, ta, traders, items);
        }
        trades.add(t);

        if (permanent) t.setNumMeetings(1);
        else t.setNumMeetings(2);

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
    private List<Trade> getUserTrades(String username) {
        List<Trade> userTrades = new ArrayList<>();
        for (Trade trade : trades){
            if (trade.getTraders().contains(username)) userTrades.add(trade);
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


    private HashMap<String, HashMap<String, Integer>> getExchangeData(Trade t){

        HashMap<String, HashMap<String, Integer>> exchangeData = new HashMap<>();

        String trader;
        HashMap<String, Integer> traderData;

        int i = 0;
        while(i < t.getTraders().size()){
            trader = t.getTraders().get(i);
            traderData = new HashMap<>();

            traderData.put("SENT", t.getItemsOriginal().get(i));
            traderData.put("RECEIVED", t.getItemsExchanged().get(i));
            traderData.put("FINAL", t.getItemsFinal().get(i));

            exchangeData.put(trader, traderData);

            i++;
        }
        return exchangeData;
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
        Trade t = getTrade(tradeNumber);
        boolean b = t.setStatus(status);
        if(b){
            notifyTradeChange(getExchangeData(t), status);
        }
        return b;
    }





    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to set the Status of
     * @return whether the Trade is Permanent
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public boolean isPermanent(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).isPermanent();
    }

    /** Returns a List of Traders (i.e., their usernames) involved in this trade
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return Usernames of Traders involved in this trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public List<String> getTraders(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getTraders();
    }

    /** Returns a List with a length equal to that of the number of traders involved in the trade (given by the
     * tradeNumber). At each index, store the ID of the item that is involved in the trade and was originally owned by
     * the Trader at the given index in getTraders(). Iff the specified Trader has no item that he/she is involving in
     * trade and originally owned, store null at the particular index.
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return A list of item IDs involved in the trade based on the original owners
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public List<Integer> getItemsOriginal(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getItemsOriginal();
    }

    /** Returns a List with a length equal to that of the number of traders involved in the trade (given by the
     * tradeNumber). At each index, store the ID of the item that is involved in the trade and will be held by the
     * Trader at the given index in getTraders() after the first transaction. Iff the specified Trader has no item that
     * he/she is involving in trade and originally owned, store null at the particular index.
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return A list of item IDs involved in the trade based on the owners after the first transaction
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public List<Integer> getItemsExchanged(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getItemsExchanged();
    }

    /** Returns a List with a length equal to that of the number of traders involved in the trade (given by the
     * tradeNumber). At each index, store the ID of the item that is involved in the trade and will be own by the
     * Trader at the given index in getTraders() at the end of the trade. Iff the specified Trader will not receive an
     * item that at the end of the trade, store null at that particular index.
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return A list of item IDs involved in this trade based on the original owners
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public List<Integer> getItemsFinal(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getItemsFinal();
    }


    /** Return the name of the algorithm of the given Trade that determines how items are exchanged.
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return The (given) trade's algorithm's name.
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public String getAlgorithmName(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getAlgorithmName();
    }




    //Methods regarding Warnings below


    /** Increases the number of warnings of the given Trade by 1. If the number of warnings reaches the maximum number
     * of warnings, the trade is automatically cancelled
     *
     * @param tradeNumber The TradeNumber of the Trade that you'd like to warn
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public void warn(int tradeNumber) throws TradeNumberException{
        getTrade(tradeNumber).warn();
    }


    /** Resets the warnings of the given Trade.
     *
     * @param tradeNumber The TradeNumber of the Trade that you'd like to reset the warnings of
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public void resetWarnings(int tradeNumber) throws TradeNumberException{
        getTrade(tradeNumber).resetWarnings();
    }


    /** Sets the Maximum Number of Warnings for this Trade
     *
     * @param tradeNumber The TradeNumber of the Trade that you'd like to set the Maximum Warnings of
     * @param maxWarnings What the new Maximum Number of Warnings for this Trade will be
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public void setMaxWarnings(int tradeNumber, int maxWarnings) throws TradeNumberException{
        getTrade(tradeNumber).setMaxWarnings(maxWarnings);
    }


    //Meeting related Methods Below


    /** Set the meetings for this trade.
     *
     * @param tradeNumber The TradeNumber of the Trade you're modifying
     * @param meetings What the meetings for this trade will be set to.
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     * @throws TradeCancelledException Thrown if the Trade is cancelled
     * @throws MaxNumMeetingsExceededException Thrown iff meetings.size > getTotalNumMeetings()
     */
    public void setMeetings(int tradeNumber, List<Integer> meetings)
            throws TradeNumberException, TradeCancelledException, MaxNumMeetingsExceededException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        if(meetings.size() > t.getTotalNumMeetings()) throw new MaxNumMeetingsExceededException();
        t.setMeetings(meetings);
    }


    /** Add a meetings to this trade.
     *
     * @param tradeNumber The TradeNumber of the Trade you're modifying
     * @param meetingID The meeting that you're adding to this trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     * @throws TradeCancelledException Thrown if the Trade is cancelled
     * @throws MaxNumMeetingsExceededException Thrown if this action will cause the Trade to have too many meetings
     */
    public void addMeeting(int tradeNumber, int meetingID)
            throws TradeNumberException, TradeCancelledException, MaxNumMeetingsExceededException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        if(t.getMeetings().size() >= t.getTotalNumMeetings()){throw new MaxNumMeetingsExceededException();}
        t.addMeeting(meetingID);
    }


    /** Remove the last meeting that has been suggested in the trade
     *
     * @param tradeNumber The Trade you're trying to modify
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     * @throws TradeCancelledException Thrown if the Trade is cancelled
     */
    public void removeLastMeeting(int tradeNumber) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.removeLastMeeting();
    }


    /** Remove the last meeting that has been suggested in the trade
     *
     * @param tradeNumber The Trade you're trying to modify
     * @param meetingID The Meeting you want to remove from the given Trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     * @throws TradeCancelledException Thrown if the Trade is cancelled
     * @throws MeetingNotInTradeException Thrown if the given Meeting is not in the given Trade
     */
    public void removeMeeting(int tradeNumber, int meetingID) throws TradeNumberException, TradeCancelledException,
            MeetingNotInTradeException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        try{t.removeMeeting(meetingID);}catch(IndexOutOfBoundsException e){throw new MeetingNotInTradeException();}
    }



    /** Return the total number of meetings that should occur in this trade (given by the Trade Number)
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return The total number of meetings that should occur in this trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public int getTotalNumMeetings(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getTotalNumMeetings();
    }


    /** Set the total number of meetings that should occur in this trade to numMeetings
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to modify
     * @param numMeetings The total number of meetings that should occur for this trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     * @throws TradeCancelledException Thrown if Trade was cancelled
     */
    public void setNumMeetings(int tradeNumber, int numMeetings) throws TradeNumberException, TradeCancelledException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        t.setNumMeetings(numMeetings);
    }


    /** Return a list consisting of all the Meeting IDs of the given Trade
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return A list consisting of all the Meeting IDs for said Trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public List<Integer> getMeetings(int tradeNumber) throws TradeNumberException{
        return getTrade(tradeNumber).getMeetings();
    }


    //Methods Regarding Trade Acceptance Below

    /** Return the usernames of the Traders that haven't accepted this trade yet
     *
     * @param tradeNumber The tradeNumber of the Trade that you want to get information about
     * @return The traders that haven't accepted the trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     */
    public List<String> getUnacceptedTraders(int tradeNumber) throws TradeNumberException {
        return getTrade(tradeNumber).getUnacceptedTraders();
    }


    /** Record the fact that the trader has accepted the trade (given by the TradeNumber).
     * If all traders have accepted the trade, automatically update the trade's status
     *
     * @param tradeNumber The tradeNumber of the Trade that is going to be accepted
     * @param trader The trader accepting the trade
     * @throws TradeNumberException Thrown if no Trade has the given TradeNumber
     * @throws TradeCancelledException Thrown if the Trade is Cancelled
     * @throws WrongTradeAccountException Thrown if the Trader is not involved in the Trade
     */
    public void acceptTrade(int tradeNumber, String trader)
            throws TradeNumberException, TradeCancelledException, WrongTradeAccountException{
        Trade t = getTrade(tradeNumber);
        checkTradeCancelled(t);
        try{t.acceptTrade(trader);}
        catch(IndexOutOfBoundsException e){throw new WrongTradeAccountException();}

        if(t.checkAccepted()){
            for(Integer itemID: t.getItemsOriginal()){
                if(itemID != null){
                    tradeItemCanceller(itemID);
                }
            }
        }
    }


    //Search Methods Below

    /**
     * Gets all relevant data for a trade with given tradeNumber in a HashMap format. The respective keys and values:
     * (1) id: id of Trade (number, single element)
     * (2) status: status of Trade (number, single element)
     * (3) type: the type of Trade based on algorithm used (name, single element)
     * (4) traders: the users participating in the trade (names, two or more elements)
     * (5) items original: the item ids based on who originally owned them (numbers, one or more elements)
     * (6) items exchanged: the item ids based on after they are exchanged (numbers, one or more elements)
     * (7) items final: the item ids based on after the trade is finished (numbers, one or more elements)
     * (8) meetings: the meeting ids part of the trade (numbers, zero or more elements)
     * (9) unaccepted: the users who have not yet accepted the trade (names, one or more elements)
     * (10) warnings: the number of warnings (number, single element)
     * (11) max warnings: the maximum number of warnings: (number, single element)
     *
     * @param tradeNumber the tradeNumber of the Trade getting data of
     * @return HashMap with all relevant data for a Trade
     * @throws TradeNumberException Thrown if no Trade has the given tradeNumber
     */
    public HashMap<String, List<String>> getTradeData(int tradeNumber) throws TradeNumberException {
        HashMap<String, List<String>> tradeData = new HashMap<>();
        Trade trade = getTrade(tradeNumber);

        tradeData.put("id", Collections.singletonList(String.valueOf(trade.getTradeNumber())));
        tradeData.put("status", Collections.singletonList(String.valueOf(trade.getStatus())));
        tradeData.put("type", Collections.singletonList(trade.getAlgorithmName()));
        tradeData.put("traders", trade.getTraders());
        tradeData.put("items original", getStringList(trade.getItemsOriginal()));
        tradeData.put("items exchanged", getStringList(trade.getItemsExchanged()));
        tradeData.put("items final", getStringList(trade.getItemsFinal()));
        tradeData.put("meetings", getStringList(trade.getMeetings()));
        tradeData.put("unaccepted", trade.getUnacceptedTraders());
        tradeData.put("warnings", Collections.singletonList(String.valueOf(trade.getWarnings())));
        tradeData.put("max warnings", Collections.singletonList(String.valueOf(trade.getMaxWarnings())));
        return tradeData;
    }

    /**
     * Gets all relevant data for all trades in given list of tradeNumbers in HashMap format
     *
     * @param trades list of tradeNumbers of the Trades getting data of
     * @return List of HashMaps, with each element being the data of a Trade
     * @throws TradeNumberException Thrown if no Trade has one of the given tradeNumbers
     */
    public List<HashMap<String, List<String>>> getTradesData(List<Integer> trades) throws TradeNumberException {
        List<HashMap<String, List<String>>> tradesData = new ArrayList<>();
        for (Integer tradeNumber: trades) {
            tradesData.add(getTradeData(tradeNumber));
        }
        return tradesData;
    }

    private List<String> getStringList(List<Integer> integerList) {
        List<String> stringList = new ArrayList<>(integerList.size());
        for (Integer integer : integerList) {
            stringList.add(String.valueOf(integer));
        }
        return stringList;
    }


    /** Return the Trade that is involved with the given meeting
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return The Trade ID that is involved with the given meeting.
     * @throws NoTradeWithMeetingIDException Thrown when no Trade is involved with the given Meeting ID.
     */
    public int getTradeWithMeeting(int meetingID) throws NoTradeWithMeetingIDException{
        for (Trade t : trades) {
            if (t.getMeetings().contains(meetingID)) {
                return t.getTradeNumber();
            }
        }
        throw new NoTradeWithMeetingIDException();
    }


    /** Return all Trades (including cancelled ones) involving the given user
     *
     * @param username The username of user you're interested in
     * @return A list containing the TradeIDs of all Trades that involve the given user
     */
    public List<Integer> getTradesWithUser(String username){
        List<Integer> userTrades = new ArrayList<>();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                userTrades.add(t.getTradeNumber());
            }
        }
        return userTrades;
    }


    /** Return all accepted Trades that have no meetings suggested yet and involve the given user.
     *
     * @param username The username of user you're interested in
     * @return A list containing the TradeIDs of all accepted Trades that involve the given user
     */
    public List<Integer> getAcceptedTradesWithUser(String username){
        List<Integer> acceptedUserTrades = new ArrayList<>();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                if(t.getStatus() == 1 && t.getMeetings().size() == 0){
                    acceptedUserTrades.add(t.getTradeNumber());
                }
            }
        }
        return acceptedUserTrades;
    }

    /**
     * Return all trades that have at least one complete meeting but still require more meetings to be completed
     *
     * @param username The username of user you're interested in
     * @return A list containing the TradeIDs of all unfinished Trades that involve the given user
     */
    public List<Integer> getUnfinishedTradesWithUser(String username) {
        List<Integer> unfinishedTrades = new ArrayList<>();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                if (t.getStatus() == 2 && t.getTotalNumMeetings() > 1) unfinishedTrades.add(t.getTradeNumber());
            }
        }
        return unfinishedTrades;
    }


    /** Return all active Trades that involved the given user.
     *
     * @param username The username of user you're interested in
     * @return A list containing the TradeIDs of all active Trades that involve the given user
     */
    public List<Integer> getActiveTradesWithUser(String username){
        List<Integer> activeUserTrades = new ArrayList<>();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                if(t.getStatus() == 1 | t.getStatus() == 2){
                    activeUserTrades.add(t.getTradeNumber());
                }
            }
        }
        return activeUserTrades;
    }


    /** Return all pending Trades with the given user involved
     *
     * @param username The username of user you want to check
     * @return A list containing all the TradeIDs of pending Trades that have the given user involved
     */
    public List<Integer> getInactiveTradesWithUser(String username) {
        List<Integer> inactiveUserTrades = new ArrayList<>();
        for (Trade t : trades) {
            if (t.getTraders().contains(username)) {
                if(t.getStatus() == 0){
                    inactiveUserTrades.add(t.getTradeNumber());
                }
            }
        }
        return inactiveUserTrades;
    }

    public List<Integer> getInactiveUserOffers(String username) throws TradeNumberException {
        List<Integer> inactiveUserOffers = new ArrayList<>();
        List<Integer> inactiveUserTrades = getInactiveTradesWithUser(username);
        for (Integer tradeNumber: inactiveUserTrades) {
            if (!getTrade(tradeNumber).getTradeInitializer().equals(username)) inactiveUserOffers.add(tradeNumber);
        }
        return inactiveUserOffers;
    }


    /** Return all Trades with the given Item (This includes cancelled trades)
     *
     * @param itemID The itemID of the given item.
     * @return A list containing all the TradeNumbers of the Trades with the given Item
     */
    public List<Integer> getTradesWithItem(int itemID){
        List<Integer> tradesWithItems = new ArrayList<>();
        for (Trade t : trades) {
            if (t.getItemsOriginal().contains(itemID)) {
                tradesWithItems.add(t.getTradeNumber());
            }
        }
        return tradesWithItems;
    }


    private void tradeItemCanceller(int itemID){
        for (Trade t : trades) {
            if (t.getItemsOriginal().contains(itemID)) {
                if(t.getStatus() == 0){
                    t.setStatus(-1);
                }
            }
        }
    }

    /** Return the Trade ID of the active trade that contains the given item. If no Trade is both active and contains
     * the given item, return 0.
     *
     * @param itemID The ID of the item you're looking for
     * @return The ID of the active trade with the item, or 0 if no trade contains the item
     */
    public int getActiveTradeWithItem(int itemID){
        for (Trade t : trades) {
            if (t.getItemsOriginal().contains(itemID)) {
                if(t.getStatus() == 1 | t.getStatus() == 2){return t.getTradeNumber();}
            }
        }
        return 0;
    }


    /** Return the TradeNumbers of all the Trades that have been accepted but haven't been completed
     *
     * @return A list of all the TradeNumbers of the Trades that have been accepted but haven't been completed
     */
    public List<Integer> getActiveTrades(){
        List<Integer> activeTrades = new ArrayList<>();
        for (Trade t : trades) {
            if(t.getStatus() == 1 | t.getStatus() == 2){
                activeTrades.add(t.getTradeNumber());
            }
        }
        return activeTrades;
    }


    /** Return the TradeNumbers of all the Trades that have been completed
     *
     * @return A list of all the TradeNumbers of the Trades that have been completed
     */
    public List<Integer> getCompletedTrades(){
        List<Integer> completedTrades = new ArrayList<>();
        for (Trade t : trades) {
            if(t.getStatus() == 3){
                completedTrades.add(t.getTradeNumber());
            }
        }
        return completedTrades;
    }




    //Meeting and Trade Observer Pattern below


    /** Record the fact that the given meeting has been accepted
     *
     * @param meetingID The ID of the accepted meeting
     */
    public void updateAccepted(int meetingID){
        try{
            resetWarnings(getTradeWithMeeting(meetingID));
            Trade t = getTrade(getTradeWithMeeting(meetingID));
            if(t.getMeetings().get(0) == meetingID){
                setStatus(getTradeWithMeeting(meetingID), 2);
            }
        }
        catch(TradeNumberException | NoTradeWithMeetingIDException ignored){}
    }


    /** Record the fact that the given meeting has been confirmed
     *
     * @param meetingID The ID of the confirmed meeting
     */
    public void updateConfirmed(int meetingID){
        try{
            Trade t = getTrade(getTradeWithMeeting(meetingID));
            if(t.getMeetings().size() == t.getTotalNumMeetings()){
                setStatus(getTradeWithMeeting(meetingID), 3);
            }
        }
        catch(TradeNumberException | NoTradeWithMeetingIDException ignored){}
    }


    /** Record the fact that the given meeting has been cancelled
     *
     * @param meetingID The ID of the cancelled meeting
     */
    public void updateCancelled(int meetingID){
        try{
            Trade t = getTrade(getTradeWithMeeting(meetingID));
            t.removeMeeting(meetingID);
            }
        catch(TradeNumberException | NoTradeWithMeetingIDException ignored){}
    }



    //Item and Trade Observer Pattern below


    /** Add an observer to this subject/observed object
     *
     * @param tradeObserver The newly-added observer for this object
     */
    public void attachTradeObserver(TradeObserver tradeObserver){
        observers.add(tradeObserver);
    }


    /** Remove an observer from this subject/observed object
     *
     * @param tradeObserver The recently-removed observer from this object
     */
    public void detachTradeObserver(TradeObserver tradeObserver){
        observers.remove(tradeObserver);
    }


    /** Notify the observers that a Trade's status has changed
     *
     * @param exchangeData A HashMap representing the Exchange Data of the Trade
     * @param newStatus The new Status of the Trade
     */
    public void notifyTradeChange(HashMap<String, HashMap<String, Integer>> exchangeData, int newStatus){
        for (TradeObserver tradeObserver: observers) {
            tradeObserver.updateTradeChange(exchangeData, newStatus);
            System.out.print(exchangeData);
        }
    }




    //Other Methods

    /**
     * Returns a list of at most the three most recent trades a user with given username has participated in
     *
     * @param username username of user
     * @return List containing lists with the ids of the items that were a part of the most recent trades
     */
    public List<List<Integer>> recentItemsTraded(String username) {
        List <Trade> userTrades = getUserTrades(username);
        return tam.recentItemsTraded(userTrades);
    }

    /**
     * Returns a list of at most the three most frequent trading partners for the user with given username
     *
     * @param username username of the user
     * @return list of the usernames of the most frequent trading partners
     */
    public List<String> frequentTradePartners(String username)  {
        List <Trade> userTrades = getUserTrades(username);
        return tam.frequentTradePartners(username, userTrades);
    }
}