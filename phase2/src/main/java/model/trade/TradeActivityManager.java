package main.java.model.trade;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Use case class for checking a user's Trade activity, and to check if the program should freeze their account
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

class TradeActivityManager {

    //Methods related to freezing a user

    /**
     * Checks if a user with given username should be frozen based on if they violate any of the trade thresholds
     * There are three thresholds that are checked:
     * (1) Borrow Threshold: if a user has borrowed more than they have lent out
     * (2) Incomplete Threshold: if a user has too many incomplete trades
     * (3) Weekly Threshold: if a user has traded too much in one week
     * If a user violates any of these thresholds, a string representing the violation is added into a list, and a list
     * of reasons is returned.
     * If the list is empty, the user should not be frozen.
     *
     * @param username username of the user
     * @param borrowThreshold threshold for borrowing more than lending
     * @param incompleteThreshold threshold for too many incomplete trades
     * @param weeklyThreshold threshold for too many trades in one week
     * @return a list of reasons why that user should be frozen. Empty if there are no reasons
     */
    protected List<String> checkUserShouldFreeze(String username, List<Trade> userTrades, int borrowThreshold,
                                        int incompleteThreshold, int weeklyThreshold) {
        List<String> reasonsToFreeze = new ArrayList<>();
        int borrowScore = 0;
        int incompleteScore = 0;
        int weeklyScore = 0;
        for (Trade trade : userTrades) {
            List<String> traders = trade.getTraders();
            List<Integer> items = trade.getItemsOriginal();
            LocalDateTime meetingTime = trade.getCompletionTime();
            int status = trade.getStatus();
            if (status == 1 || status == 2 || status == 3) borrowScore += checkBorrowThreshold(username, traders, items);
            if (checkWeeklyThreshold(meetingTime)) weeklyScore++;
            if (checkIncompleteThreshold(status)) incompleteScore++;
        }
        if (borrowScore > borrowThreshold) reasonsToFreeze.add("BORROW");
        if (incompleteScore > incompleteThreshold) reasonsToFreeze.add("INCOMPLETE");
        if (weeklyScore > weeklyThreshold) reasonsToFreeze.add("WEEKLY");
        return reasonsToFreeze;

    }

    private int checkBorrowThreshold(String username, List<String> traders, List<Integer> items) {
        if (traders.get(0).equals(username) && items.size() == 1) return 1;
        else if (traders.get(1).equals(username) && items.size() == 1) return -1;
        else return 0;
    }

    private boolean checkWeeklyThreshold(LocalDateTime meetingTime) {
        return (!(meetingTime == null) && meetingTime.isAfter(LocalDateTime.now().minusDays(7)));
    }

    private boolean checkIncompleteThreshold(int tradeStatus) {
        return tradeStatus == -1;
    }

    //Methods related to the activity of a user

    /**
     * Returns a list of at most the three most recent trades in userTrades
     *
     * @param userTrades list of trades a user has participated in
     * @return List containing lists with the ids of the items that were a part of the most recent trades
     */
    protected List<List<Integer>> recentItemsTraded(List<Trade> userTrades) {
        List<List<Integer>> threeRecentItems = new ArrayList<>();
        TreeMap<LocalDateTime, Trade> orderedTrades = new TreeMap<>();
        for (Trade trade : userTrades) {
            LocalDateTime time = trade.getCompletionTime();
            if (time != null) orderedTrades.put(time, trade);
        }
        //Citation: https://stackoverflow.com/questions/6928758/get-the-last-3-values-of-a-treemap
        NavigableSet<LocalDateTime> reverseUserTrades = orderedTrades.descendingKeySet();
        int i = 0;
        for(Iterator<LocalDateTime> it = reverseUserTrades.iterator(); it.hasNext() && i<3;) {
            LocalDateTime time = it.next();
            threeRecentItems.add(orderedTrades.get(time).getItemsOriginal());
            i++;
        }
        //End of citation
        return threeRecentItems;
    }

    /**
     * Returns a list of at most the three most frequent trading partners for the user with given username
     * @param username username of the user
     * @param userTrades list of trades the user has participated in
     * @return list of the usernames of the most frequent trading partners
     */
    protected List<String> frequentTradePartners(String username, List<Trade> userTrades) {
        List<String> threeMostFrequentPartners = new ArrayList<>();
        Map<String, Integer> tradingPartners = getTradePartners(username, userTrades);
        Map.Entry<String, Integer> maxEntry = null;
        while (threeMostFrequentPartners.size() < 3 || threeMostFrequentPartners.size() < tradingPartners.size()) {
            //Citation: https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
            for (Map.Entry<String, Integer> entry : tradingPartners.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) maxEntry = entry;
            }
            //End of citation
            assert maxEntry != null;
            threeMostFrequentPartners.add(maxEntry.getKey());
            tradingPartners.replace(maxEntry.getKey(), 0);
        }
        return threeMostFrequentPartners;
    }

    private Map<String, Integer> getTradePartners(String username, List<Trade> userTrades) {
        Map<String, Integer> tradingPartners = new HashMap<>();
        for (Trade trade : userTrades) {
            List<String> tradeParticipants = trade.getTraders();
            int status = trade.getStatus();
            if (status == 1 || status == 2 || status == 3) {
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
