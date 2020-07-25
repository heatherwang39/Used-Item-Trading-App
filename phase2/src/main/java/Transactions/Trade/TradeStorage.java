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
            if (t.getTraders().contains(tradesWithItems)) {
                tradesWithItems.add(t.getTradeNumber());
            }
        }
        return tradesWithItems;
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
























































    //TODO: Implement the Mediator Pattern between TradeStorage and MeetingStorage


    //TODO: Examine Fadi's Methods (below). They may need to change due to the dramatic changes in the Trade Class




    /**
     * Returns whether the user with given username should be frozen based on if they borrowed more than they have lent.
     *
     * @param username username of the user
     * @param tradeThreshold the number of trades allowed
     * @return true if user should be frozen, false if not
     */
    public boolean checkUserShouldFreeze(String username, int tradeThreshold) {
        int freezeScore = 0;
        for (OneWayTrade oneWayTrade : getOneWayTrades()) {
            if (oneWayTrade.getSender().equals(username)) freezeScore--;
            else if (oneWayTrade.getReceiver().equals(username)) freezeScore++;
        }
        return freezeScore > tradeThreshold;
    }

    private List<OneWayTrade> getOneWayTrades() {
        List<OneWayTrade> oneWayTrades = new ArrayList<>();
        for (Trade trade : trades) {
            if (trade.isOneWay() && checkOngoingComplete(trade)) oneWayTrades.add((OneWayTrade) trade);
        }
        return oneWayTrades;
    }



    private boolean checkOngoingComplete(Trade trade) {
        return (trade.getStatus() == 1 || trade.getStatus() == 2);
    }


    /**
     * Returns whether the user with given username should be frozen based on if they participated in too many trades the past week.
     *
     * @param username username of the user
     * @param weeklyThreshold the number of trades in a week allowed
     * @param oneWeekAgo the date of exactly one week ago
     * @return true if user should be frozen, false if not
     */
    public boolean checkUserWeeklyTrades(String username, int weeklyThreshold, LocalDateTime oneWeekAgo) {
        int numTradesInWeek = 0;
        try {
            for (Trade trade : trades) {
                LocalDateTime meetingTime = trade.getMeetingTime(1);
                if (trade.getTraders().contains(username) && !(meetingTime == null) && meetingTime.isAfter(oneWeekAgo)) {
                    numTradesInWeek++;
                }
            }
        } catch (MeetingNumberException e) {
            return false;
        }
        return numTradesInWeek > weeklyThreshold;
    }


    /**
     * Returns whether the user with given username should be frozen based on if they have too many incomplete trades.
     * @param username username of the user
     * @param incompleteThreshold the number of incomplete trades allowed
     * @return true if user should be frozen, false if not
     */
    public boolean checkUserIncompleteTrades(String username, int incompleteThreshold) {
        int numIncompleteTrades = 0;
        for (Trade trade : trades) {
            if (trade.getTraders().contains(username) && trade.getStatus() == -1) numIncompleteTrades++;
        }
        return numIncompleteTrades > incompleteThreshold;
    }


    /**
     * Returns a list of items from at most the three most recent trades user with given username participated in.
     * @param username username of the user
     * @return list of list of the item ids of items involved in the trades
     */
    public List<List<Integer>> recentTradedItems(String username) {
        List<List<Integer>> threeRecentItems = new ArrayList<>();
        TreeMap<LocalDateTime, Trade> userTrades = new TreeMap<>();
        for (Trade trade : trades) {
            if (trade.getTraders().contains(username) && checkOngoingComplete(trade)){
                try {
                    LocalDateTime time = trade.getMeetingTime(1);
                    if (time!= null) userTrades.put(time, trade);
                } catch (MeetingNumberException e) {
                    return threeRecentItems;
                }
            }
        }
        //The following code is from the citation: https://stackoverflow.com/questions/6928758/get-the-last-3-values-of-a-treemap
        NavigableSet<LocalDateTime> reverseUserTrades = userTrades.descendingKeySet();
        int i=0;
        for(Iterator<LocalDateTime> it = reverseUserTrades.iterator(); it.hasNext() && i<3;) {
            LocalDateTime time = it.next();
            threeRecentItems.add(userTrades.get(time).getItemsOriginal());
            i++;
        }
        //End of citation
        return threeRecentItems;
    }



    /**
     * Returns a set of the tree most frequent trading partners for user with given username.
     * @param username username of the user
     * @return set of usernames of the most frequent trading partners
     */
    public Set<String> frequentTradingPartners(String username) {
        Map<String, Integer> threeMostFrequentPartners = new HashMap<>();
        Map<String, Integer> tradingPartners = getTradingPartners(username);
        if (tradingPartners.isEmpty()) return tradingPartners.keySet();
        int minTrades = Collections.max(tradingPartners.values());
        String minUser = null;
        for (Map.Entry<String, Integer> entry : tradingPartners.entrySet()){
            if (threeMostFrequentPartners.size() < 3) {
                threeMostFrequentPartners.put(entry.getKey(), entry.getValue());
                if (entry.getValue() < minTrades) {
                    minTrades = entry.getValue();
                    minUser = entry.getKey();
                }
            }
            else {
                if (minTrades < entry.getValue()) {
                    threeMostFrequentPartners.remove(minUser);
                    threeMostFrequentPartners.put(entry.getKey(), entry.getValue());
                    minTrades = Collections.max(threeMostFrequentPartners.values());
                    for (Map.Entry<String, Integer> secondEntry : threeMostFrequentPartners.entrySet()){
                        if (secondEntry.getValue() <= minTrades) {
                            minTrades = entry.getValue();
                            minUser = entry.getKey();
                        }
                    }
                }
            }
        }
        return threeMostFrequentPartners.keySet();
    }

    private Map<String, Integer> getTradingPartners(String username) {
        Map<String, Integer> tradingPartners = new HashMap<>();
        for (Trade trade : trades) {
            List<String> tradeParticipants = trade.getTraders();
            if (tradeParticipants.contains(username) && checkOngoingComplete(trade)){
                String otherTrader;
                if (tradeParticipants.get(0).equals(username)) otherTrader = tradeParticipants.get(1);
                else otherTrader = tradeParticipants.get(0);
                if (!tradingPartners.containsKey(otherTrader)) tradingPartners.put(otherTrader, 1);
                else tradingPartners.replace(otherTrader, tradingPartners.get(otherTrader) + 1);
            }
        }
        return tradingPartners;
    }
}