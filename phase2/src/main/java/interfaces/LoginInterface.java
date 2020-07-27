package main.java.interfaces;

import java.util.List;

/**
 * An Interface for the actions of an account that is able to log in
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface LoginInterface {

    /**
     * Checks if username and password are valid, exisiting alphanumeric + dash/underscore strings
     *
     * @param username the input username
     * @param password the input password
     */
    void signIn(String username, String password);

    /**
     * Displays the inventory of the user
     *
     * @param username username of the user
     * @return list of item names
     */
    List<String> viewInventory(String username);

    /**
     * Displays the wishlist of the user
     *
     * @param username username of the user
     * @return list of item names
     */
    List<String> viewWishlist(String username);
}