package main.java.system;

import java.util.List;

/**
 * An Interface for the actions of an account that is able to set meetings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Meetable {

    /**
     * Initializes a trade request meant to be sent by the user
     *
     * @param itemId id of the item
     * @return true if the request is created, false otherwise
     */
    boolean createRequest(int itemId);

    /**
     * Allows a user to request a new meeting spot or accept the suggested location
     *
     * @param tradeNumber id of the trade a meeting is being set to
     * @param meetings what the meetings for this trade will be set to.
     */
    String setMeeting(int tradeNumber, List<Integer> meetings);
}