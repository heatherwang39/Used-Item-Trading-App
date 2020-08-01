package main.java.system;

import java.util.List;

/**
 * An Interface for the actions of an account that is able to view it's activity
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Viewable {

    /**
     * Allows a user with the given username to check their three most frequent trading partners
     *
     * @return list of frequent trading partners
     */
    List<String> showFrequentPartners();

    /**
     * Allows a user with the given username to check the items in their three most recent trades
     *
     * @return list containing lists of the three most recently traded items
     */
    List<List<String>> showRecentItems();

    /**
     * Shows the user what offers have been made to them
     *
     * @return list of trades offered
     */
    List<List<String>> showOffers();

    /**
     * Shows the user what trades they are in are currently active
     *
     * @return list of active trades
     */
    List<List<String>> showActiveTrades();
}