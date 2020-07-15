package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract account in the main.java.Trade system representing a single user or admin of the program. All accounts
 * store the username, password, and email, along with the account's inventory and wishlist as lists containing item
 * IDs.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class Account implements Serializable, Entity {

    private final String username;
    private final String password;
    private final String email;
    private final List<Integer> wishlist;
    private final List<Integer> inventory;
    private final List<Integer> tradesOffered;
    private final List<Integer> tradesReceived;



    /**
     * Class constructor
     * @param username account username
     * @param password account password
     * @param email account email address
     */
    public Account(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        inventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradesOffered = new ArrayList<>();
        tradesReceived = new ArrayList<>();
    }

    /**
     * Check the privilege level of the account
     * @return if the account is an admin
     */
    abstract boolean isAdmin();

    /**
     * Verify the password's correctness
     * @param password input password
     * @return if input password matches stored password
     */
    public boolean isPassword(String password){
        return password.equals(this.password);
    }

    /**
     * Get the account username
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Get the account password
     * @return password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Get the account email
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * Get a shallow copy of account inventory
     * @return inventory
     */
    public List<Integer> getInventory() {return new ArrayList<>(inventory); }

    /**
     * Get a shallow copy of account wishlist
     * @return wishlist
     */
    public List<Integer> getWishlist() { return new ArrayList<>(wishlist); }

    /**
     * Add an item ID to account inventory
     * @param itemID item ID to be added
     */
    public void addInventory(int itemID) { inventory.add(itemID); }

    /**
     * Add an item ID to account wishlist
     * @param itemID item ID to be added
     */
    public void addWishlist(int itemID) { wishlist.add(itemID); }

    /**
     * Remove an item ID from account inventory
     * @param id item id to be removed
     */
    public void removeInventory(int id) {
        int i = 0;
        for (Integer itemID : inventory){
            if (itemID == id) break;
            i++;
        }
        inventory.remove(i);
    }

    /**
     * Remove an item from account wishlist
     * @param id item id to be removed
     */
    public void removeWishlist(int id) {
        int i = 0;
        for (Integer itemID : wishlist){
            if (itemID == id) break;
            i++;
        }
        wishlist.remove(i);
    }

    /**
     * Get a shallow copy of trades offered
     * @return trade numbers
     */
    public List<Integer> getTradesOffered() {return new ArrayList<>(tradesOffered); }

    /**
     * Get a shallow copy of trades received
     * @return trade numbers
     */
    public List<Integer> getTradesReceived() { return new ArrayList<>(tradesReceived); }

    /**
     * Add a trade to trades offered
     * @param tradeNumber trade number
     */
    public void addTradesOffered(int tradeNumber) { tradesOffered.add(tradeNumber); }

    /**
     * Add a trade to trades received
     * @param tradeNumber trade number
     */
    public void addTradesReceived(int tradeNumber) { tradesReceived.add(tradeNumber); }

    /**
     * Remove a trade from trades offered
     * @param tradeNumber trade number
     */
    public void removeTradesOffered(int tradeNumber) { tradesOffered.remove(tradeNumber); }

    /**
     * Remove a trade from trades received
     * @param tradeNumber trade number
     */
    public void removeTradesReceived(int tradeNumber) { tradesReceived.remove(tradeNumber); }

    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a 2D List
     */
    public List<List<String>> getData(){return new ArrayList<>();}
}
