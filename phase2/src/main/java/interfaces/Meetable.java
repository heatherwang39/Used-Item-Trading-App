package main.java.interfaces;

/**
 * An Interface for the actions of an account that is able to set meetings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Meetable {

    //The return types and the parameters of this Interface will need to be changed

    /**
     * Initializes a trade request meant to be sent by the user
     *
     * @param username username of the user
     * @param itemId id of the item
     * @return true if the request is created, false otherwise
     */
    boolean createRequest(String username, int itemId);

    /**
     * Allows a user to request a new meeting spot or accept the suggested location
     *
     * @param username username of the user
     */
    void setMeeting(String username);
}