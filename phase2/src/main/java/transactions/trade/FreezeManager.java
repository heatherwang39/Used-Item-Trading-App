package main.java.transactions.trade;

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
                //LocalDateTime meetingTime = ts.getMeetingTime(tradeId); //TODO: update with mediator methods
                int tradeStatus = ts.getStatus(tradeId);
                if (ts.acceptedStatus(tradeStatus)) borrowScore += checkBorrowThreshold(username, traders, items);
                //if (checkWeeklyThreshold(meetingTime)) weeklyScore++;
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
