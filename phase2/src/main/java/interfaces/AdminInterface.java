package main.java.interfaces;

import java.util.List;

/**
 * An Interface for the actions of an admin account
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface AdminInterface {

    /**
     * Promotes a user into an admin
     *
     * @param username username of user
     */
    void addAdmin(String username);

    /**
     * Updates the browse threshold
     *
     * @param newThreshold new value to replace existing threshold
     */
    void updateBrowseThreshold(int newThreshold);

    /**
     * Updates the incomplete threshold
     *
     * @param newThreshold new value to replace existing threshold
     */
    void updateIncompleteThreshold(int newThreshold);

    /**
     * Updates the weekly threshold
     *
     * @param newThreshold new value to replace existing threshold
     */
    void updateWeeklyThreshold(int newThreshold);
}