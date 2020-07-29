package main.java.interfaces;

import java.util.List;

public interface UserAccountController extends AccountController {

    //Username of the user should be stored in the controller IMO

    /**
     * Allows a user to check their three most frequent trading partners
     *
     * @return list of frequent trading partners
     */
    List<String> showFrequentPartners();

    /**
     * Allows a user to check the items in their three most recent trades
     *
     * @return list containing lists of the three most recently traded items
     */
    List<List<String>> showRecentItems();

    /**
     * Shows the user what offers have been made to them
     *
     * @return list of trades offered
     */
    List<String> showOffers();

    /**
     * Shows the user what trades they are in are currently active
     *
     * @return list of active trades
     */
    List<String> showActiveTrades();

    /**
     * Adds an item with itemId to the account with username's inventory
     *
     * @param itemId id of the item
     */
    void addItem(int itemId);

    /**
     * Adds an item with itemId to the account with username's wishlist
     *
     * @param itemId id of the item
     */
    void addWishlist(int itemId);
}
