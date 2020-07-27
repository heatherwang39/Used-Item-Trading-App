package main.java.interfaces;

/**
 * An Interface for the actions of an account that is able to set meetings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface ModeratorInterface {

    /**
     * Shows which users have violated a trade threshold, allowing the user with moderator privileges to decide
     * whether to freeze their account or not
     */
    void showFreezeUsers();

    /**
     * Shows items awaiting approval, allowing the user with moderator privileges to decide whether to approve the
     * items or not
     */
    void showItemRequests();
}