package main.java.model.trade;

import main.java.model.status.StatusNotFoundException;

import java.util.List;

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


    /** Notify the observers that a Trade with the items distributed between owners as stored in the two parallel lists
     * (inputs/parameters) has been completed.
     *
     * @param itemID A parallel list representing the IDs of items involved in the trade
     * @param newOwner A parallel list representing the usernames of the new owners of the aforementioned items
     */
    void notifyTradeComplete(List<Integer> itemID, List<String> newOwner);
}
