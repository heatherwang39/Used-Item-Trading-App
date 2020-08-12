package main.java.model.trade;

import java.util.HashMap;

/**
 * An interface for something in the trade Package that updates its observers
 * when certain changes happen.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface TradeObservee {

    /** Add an observer to this subject/observed object
     *
     * @param tradeObserver The newly-added observer for this object
     */
    void attachTradeObserver(TradeObserver tradeObserver);


    /** Remove an observer from this subject/observed object
     *
     * @param tradeObserver The recently-removed observer from this object
     */
    void detachTradeObserver(TradeObserver tradeObserver);


    /** Notify the observers that a Trade's status has changed
     *
     * @param exchangeData A HashMap representing the Exchange Data of the Trade
     * @param newStatus The new Status of the Trade
     */
    void notifyTradeChange(HashMap<String, HashMap<String, Integer>> exchangeData, int newStatus);
}
