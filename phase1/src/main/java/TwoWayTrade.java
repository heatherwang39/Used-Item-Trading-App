package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
abstract class TwoWayTrade extends Trade implements Serializable {
    private String firstTrader;
    private int firstItem;
    private String secondTrader;
    private int secondItem;


    /** Initializes an instance of TwoWayTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayTrade(String firstTrader, int firstItem,
                       String secondTrader, int secondItem){
        firstTrader = firstTrader;
        firstItem = firstItem;
        secondTrader = secondTrader;
        secondItem = secondItem;
    };


    /** Retrieve the first trader (their username) involved in this trade
     *
     * @return The first trader (their username) involved in this trade
     */
    public String getFirstTrader(){
        return firstTrader;
    }

    /** Retrieve the item the first trader (their username) sent in this trade
     *
     * @return The item the first trader (their username) sent in this trade
     */
    public int getFirstTraderItem(){
        return firstItem;
    }


    /** Retrieve the second trader (their username) involved in this trade
     *
     * @return The second trader (their username) involved in this trade
     */
    public String getSecondTrader(){
        return secondTrader;
    }


    /** Retrieve the item the second trader (their username) sent in this trade
     *
     * @return The item the second trader (their username) sent in this trade
     */
    public int getSecondTraderItem(){
        return secondItem;
    }


    /** Returns whether or not the Trade is one-way. Iff the Trade is one-way, return true.
     *
     * @return whether the Trade is one-way
     */
    public boolean isOneWay(){
        return false;
    }


    /** Returns a List of Traders (i.e., their usernames) involved in this trade
     *
     * @return Usernames of Traders involved in this trade
     */
    public List<String> getTraders(){
        List<String> traders = new ArrayList<String>();
        traders.add(getFirstTrader());
        traders.add(getSecondTrader());
        return traders;
    }

    /** Returns a List of Items (i.e., their ids) in an order based on the item's owner before the trade and the order
     *  of the Traders that getTraders() returns.
     *
     * @return The list of items involved in this trade
     */
    public List<Integer> getItems(){
        List<Integer> items = new ArrayList<Integer>();
        items.add(getFirstTraderItem());
        items.add(getSecondTraderItem());
        return items;
    }
}