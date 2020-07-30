package main.java.systems;

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
     * Registers a new Administrative Account
     *
     * @param username the username of the new account
     * @param password the password of the new account
     * @param email the email of the new account
     */
    String registerAdmin(String username, String password, String email);

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