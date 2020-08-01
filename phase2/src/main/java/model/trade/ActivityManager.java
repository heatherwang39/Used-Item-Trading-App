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

    public List<List<Integer>> recentItemsTraded(List<Trade> userTrades) {
        TradeStorage ts = new TradeStorage(userTrades);
        List<List<Integer>> threeRecentItems = new ArrayList<>();
        TreeMap<LocalDateTime, Trade> orderedTrades = new TreeMap<>();
        for (Trade trade : userTrades) {
            //LocalDateTime time = ts.getMeetingTime(); //TODO: update with mediator methods
            //if (time != null) orderedTrades.put(time, trade);
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

    public List<String> frequentTradePartners(String username, List<Trade> userTrades) {
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