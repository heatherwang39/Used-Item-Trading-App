package main.java.model.status;

import main.java.model.trade.TradeObserver;

import java.util.List;

/**
 * An interface for something in the status Package that updates its observers
 * when certain changes happen.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface StatusObservee {

    /** Add an observer to this subject/observed object
     *
     * @param statusObserver The newly-added observer for this object
     */
    void attachStatusObserver(StatusObserver statusObserver);


    /** Remove an observer from this subject/observed object
     *
     * @param statusObserver The recently-removed observer from this object
     */
    void detachTradeObserver(StatusObserver statusObserver);


    /** Notify the observers that a status was added to a given user
     *
     * @param status The status added to the user
     * @param user The user that had a status added
     */
    void notifyStatusAdded(String status, String user);


    /** Notify the observers that a status was removed from a given user
     *
     * @param status The status removed from the user
     * @param user The user that had a status removed
     */
    void notifyStatusRemoved(String status, String user);
}