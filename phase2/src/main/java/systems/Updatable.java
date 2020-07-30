package main.java.systems;

import java.util.List;

/**
 * An Interface for the actions of an account that is able to add to their listings
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

//

public interface Updatable {

    /**
     * Adds an item with itemId to the account with username's inventory
     *
     * @param owner username of the owner of the item
     * @param name name of the item
     * @param description description of the item
     * @param tags a list of user inputted tags for the item
     */
    void addItem(String owner, String name, String description, List<String> tags);

    /**
     * Adds an item with itemId to the account with username's wishlist
     *
     * @param username username of the user
     * @param itemId id of the item
     */
    void addWishlist(String username, int itemId);
}