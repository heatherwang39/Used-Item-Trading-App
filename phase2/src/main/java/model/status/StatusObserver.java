package main.java.model.status;

import main.java.model.account.AccountNotFoundException;

/**
 * An interface for something that "observes" something in the status package
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface StatusObserver {

    /** Update the fact that a Status was added to a certain user
     *
     * @param status The status added to the user
     * @param user The user that had a status added
     */
    void updateStatusAdded(String status, String user);


    /** Update the fact that a Status was removed from a certain user
     *
     * @param status The status removed from the user
     * @param user The user that had a status removed
     */
    void updateStatusRemoved(String status, String user);
}
