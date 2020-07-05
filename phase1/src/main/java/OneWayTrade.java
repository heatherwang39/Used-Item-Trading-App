package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract class OneWayTrade extends Trade implements Serializable {
    private String sender;
    private String receiver;
    private int item; //changed item from List<main.java.Item> to main.java.Item


    /** Initializes an instance of main.java.TwoWayTrade based on the given parameters
     *
     * @param sender The trader (their username) that sent the item
     * @param receiver The trader (their username) that received the item
     * @param item The item (its ID) that was traded from the sender to the receiver
     */
    public OneWayTrade(int tradeNumber, String sender, String receiver, int item){
        super(tradeNumber);
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
    }

    /** Retrieve the item (its ID) involved with this trade
     *
     * @return The item (its ID) involved with this trade
     */
    public int getItem(){
        return item;
    }

    /** Retrieve the trader (their username) that sent the item away in this trade
     *
     * @return The trader (their username) that sent the item away in this trade
     */
    public String getSender(){
        return sender;
    }


    /** Retrieve the trader (their username) that received an item in this trade
     *
     * @return The trader (their username) that received an item in this trade
     */
    public String getReceiver(){
        return receiver;
    }


    /** Returns whether or not the Trade is one-way. Iff the Trade is one-way, return true.
     *
     * @return whether the Trade is one-way
     */
    public boolean isOneWay(){
        return true;
    }


    /** Returns a List of Traders (i.e., their usernames) involved in this trade
     *
     * @return Usernames of Traders involved in this trade
     */
    public List<String> getTraders(){
        List<String> traders = new ArrayList<String>();
        traders.add(getSender());
        traders.add(getReceiver());
        return traders;
    }


    /** Returns a List of Items (i.e., their ids) in an order based on the item's owner before the trade and the order
     *  of the Traders that getTraders() returns.
     *
     * @return The list of items involved in this trade
     */
    public List<Integer> getItems(){
        List<Integer> items = new ArrayList<Integer>();
        items.add(getItem());
        return items;
    }
}