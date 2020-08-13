package main.java.model.trade;

import java.util.HashMap;

/**
 * An interface for something that "observes" something in the trade package
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface TradeObserver {

    /** Record the fact that a Trade's status has changed
     *
     * @param exchangeData A HashMap representing the Exchange Data of the Trade
     * @param newStatus The new Status of the Trade
     */
    void updateTradeChange(HashMap<String, HashMap<String, Integer>> exchangeData, int newStatus);
}