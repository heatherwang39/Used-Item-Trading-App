package main.java.interfaces;

import java.util.List;

/**
 * An Interface for the actions of an account that is able to add to their listings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

public interface Updatable {

    /**
     * Adds an item with itemId to the account with username's inventory
     *
     * @param username username of the user
     * @param itemId id of the item
     */
    void addItem(String username, int itemId);

    /**
     * Adds an item with itemId to the account with username's wishlist
     *
     * @param username username of the user
     * @param itemId id of the item
     */
    void addWishlist(String username, int itemId);
}