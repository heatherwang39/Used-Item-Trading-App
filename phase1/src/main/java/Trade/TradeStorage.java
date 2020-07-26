package main.java.Trade;

import main.java.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;


/**
 * Use case class for initializing, storing, and returning trades.
 * @author Warren Zhu, Fadi Hareth
 * @version %I%, %G%
 * @since Phase 1
 */

public class TradeStorage {
    private List<Trade> trades = new ArrayList<>();
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
                trades = (List<Trade>) frw.readFromFile(path);
            } catch (EOFException ignored) {
            }
        } else {
            file.createNewFile();
        }
    }

    /**
     * Gets the total number of trades made
     *
     * @return the size of the trades List
     */
    public int getNumberOfTrades() {
        return trades.size();
    }

    /**
     * Initializes a new OneWayTrade based on the given parameters. Return the tradeNumber of the newly initialized
     * OneWayTrade.
     *
     * @param permanent Whether or not the Trade is to be permanent
     * @param sender    The username of the Sender of the Trade
     * @param receiver  The username of the Receiver of the Trade
     * @param item      The ID of the item involved in the Trade
     * @return The tradeNumber of the newly initialized Trade.
     * @throws IOException Cannot save to file
     */
    public int newOneWayTrade(Boolean permanent, String sender, String receiver, int item) throws IOException {
        int t;
        if (permanent) {
            t = newOWPTrade(sender, receiver, item);
        } else {
            t = newOWTTrade(sender, receiver, item);
        }
        return t;
    }

    private int newOWPTrade(String sender, String receiver, int item) throws IOException {
        Trade t = new OneWayPermanentTrade(getNumberOfTrades(), sender, receiver, item);
        trades.add(t);
        frw.saveToFile(trades,path);
        return t.getTradeNumber();
    }

    private int newOWTTrade(String sender, String receiver, int item) throws IOException {
        Trade t = new OneWayTemporaryTrade(getNumberOfTrades(), sender, receiver, item);
        trades.add(t);
        frw.saveToFile(trades,path);
        return t.getTradeNumber();
    }


    /**
     * Initializes a new TwoWayTrade based on the given parameters. Return the tradeNumber of the newly initialized
     * TwoWayTrade
     *
     * @param permanent    Whether or not the Trade is to be permanent
     * @param firstTrader  The username of the first trader.
     * @param firstItem    The ID of the item the first trader sent in this trade
     * @param secondTrader The username of the second trader.
     * @param secondItem   The ID of the item the second trader sent in this trade
     * @return The tradeNumber of the newly initialized Trade.
     * @throws IOException Cannot save to file
     */
    public int newTwoWayTrade(Boolean permanent, String firstTrader, int firstItem,
                              String secondTrader, int secondItem) throws IOException {
        int t;
        if (permanent) {
            t = newTWPTrade(firstTrader, firstItem, secondTrader, secondItem);
        } else {
            t = newTWTTrade(firstTrader, firstItem, secondTrader, secondItem);
        }
        return t;
    }

    private int newTWPTrade(String firstTrader, int firstItem, String secondTrader, int secondItem) throws IOException {
        Trade t = new TwoWayPermanentTrade(getNumberOfTrades(), firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        frw.saveToFile(trades,path);
        return t.getTradeNumber();
    }

    private int newTWTTrade(String firstTrader, int firstItem, String secondTrader, int secondItem) throws IOException {
        Trade t = new TwoWayTemporaryTrade(getNumberOfTrades(), firstTrader, firstItem, secondTrader, secondItem);
        trades.add(t);
        frw.saveToFile(trades,path);
        return t.getTradeNumber();
    }


    /**
     * Returns the Trade that corresponds to the given tradeNumber
     *
     * @param tradeNumber The tradeNumber of the Trade that is to be returned
     * @return The Trade that corresponds to the given tradeNumber
     * @throws TradeNumberException If no Trade with the given tradeNumber can be found
     */
    public Trade getTrade(int tradeNumber) throws TradeNumberException {
        for (Trade t : trades) {
            if (t.getTradeNumber() == tradeNumber) {
                return t;
            }
        }
        throw new TradeNumberException();
    }


    /**
     * Returns all the items pertaining to tradeNumber.
     *
     * @param tradeNumber The tradeNumber of the Trade
     * @return a list of Integer values which represent all the trade IDs involved in this trade
     * @throws TradeNumberException If no Trade with the given tradeNumber can be found
     */
    public List<Integer> getItemsInTrade(int tradeNumber) throws TradeNumberException {
        for (Trade t : trades) {
            if (t.getTradeNumber() == tradeNumber) {
                return t.getItemsOriginal();
            }
        }
        throw new TradeNumberException();
    }

    /**
     * Returns whether the trade with the corresponding trade number is currently ongoing
     *
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