package main.java.model.trade;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Use case class for checking a user's Trade activity.
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

class ActivityManager {

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
        TradeStorage ts = new TradeStorage(userTrades);
        List<String> threeMostFrequentPartners = new ArrayList<>();
        Map<String, Integer> tradingPartners = getTradePartners(username, userTrades, ts);
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

    private Map<String, Integer> getTradePartners(String username, List<Trade> userTrades, TradeStorage ts) {
        Map<String, Integer> tradingPartners = new HashMap<>();
        for (Trade trade : userTrades) {
            List<String> tradeParticipants = trade.getTraders();
            if (ts.acceptedStatus(trade.getStatus())) {
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