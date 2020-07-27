package main.java.interfaces;

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
     * @param username username of the user
     * @return list of frequent trading partners
     */
    List<String> showFrequentPartners(String username);

    /**
     * Allows a user with the given username to check the items in their three most recent trades
     *
     * @param username username of the user
     * @return list containing lists of the three most recently traded items
     */
    List<List<String>> showRecentItems(String username);

    /**
     * Shows the user what offers have been made to them
     *
     * @param username username of the user
     * @return list of trades offered
     */
    List<String> showOffers(String username);

    /**
     * Shows the user what trades they are in are currently active
     *
     * @param username username of the user
     * @return list of active trades
     */
    List<String> showActiveTrades(String username);
}