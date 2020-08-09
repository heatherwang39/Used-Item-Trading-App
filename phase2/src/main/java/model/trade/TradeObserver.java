package main.java.model.trade;

import main.java.model.item.ItemNotFoundException;


import java.util.List;

/**
 * An interface for something that "observes" something in the trade package
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface TradeObserver {

    /** Record the fact that a Trade with the items distributed between owners as stored in the two parallel lists
     * (inputs/parameters) has been completed.
     *
     * @param itemIDs A parallel list representing the IDs of items involved in the trade
     * @param newOwner A parallel list representing the usernames of the new owners of the aforementioned items
     */
    void updateTradeComplete(List<Integer> itemIDs, List<String> newOwner);
}