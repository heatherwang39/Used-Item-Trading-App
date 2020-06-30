package main.java;

import java.io.Serializable;

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
    public OneWayTrade(String sender, String receiver, int item){
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
}