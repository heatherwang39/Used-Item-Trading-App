package main.java.model.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Use case class for checking if a user's Trade history should suggest freezing their account.
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

class FreezeManager {

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
        TradeStorage ts = new TradeStorage(userTrades);
        List<String> reasonsToFreeze = new ArrayList<>();
        int borrowScore = 0;
        int incompleteScore = 0;
        int weeklyScore = 0;
        for (Trade trade : userTrades) {
            try {
                int tradeId = userTrades.indexOf(trade);
                List<String> traders = ts.getTraders(tradeId);
                List<Integer> items = ts.getItemsOriginal(tradeId);
                LocalDateTime meetingTime = trade.getCompletionTime();
                int tradeStatus = ts.getStatus(tradeId);
                if (ts.acceptedStatus(tradeStatus)) borrowScore += checkBorrowThreshold(username, traders, items);
                if (checkWeeklyThreshold(meetingTime)) weeklyScore++;
                if (checkIncompleteThreshold(tradeStatus)) incompleteScore++;
            } catch (TradeNumberException e){
                break;
            }
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
}
