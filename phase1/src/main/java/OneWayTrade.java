package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * An abstract class in the main.java.Transactions.Trade system representing a one-way trade in the program. In these trades,
 * only one participant sends the other participant an item.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class OneWayTrade extends Trade implements Serializable {
    private String sender;
    private String receiver;
    private int item; //changed item from List<main.java.item.Item> to main.java.item.Item


    /** Initializes an instance of main.java.TwoWayTrade based on the given parameters
     *
     * @param tradeNumber The trade number of the trade
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



    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and was originally owned by the Trader at the given
     * index in getTraders(). Iff the specified Trader has no item that he/she is involving in trade and originally
     * owned, store null at the particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    public List<Integer> getItemsOriginal(){
        List<Integer> items = new ArrayList();
        items.add(getItem());
        items.add(null);
        return items;
    }
}