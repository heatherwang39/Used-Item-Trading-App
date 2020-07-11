package main.java;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class in the main.java.Trade system representing a two-way trade in the program. In these trades,
 * both participants send each other items.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class TwoWayTrade extends Trade implements Serializable {
    private String firstTrader;
    private int firstItem;
    private String secondTrader;
    private int secondItem;


    /** Initializes an instance of TwoWayTrade based on the given parameters
     *
     * @param tradeNumber The trade number of the trade
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayTrade(int tradeNumber, String firstTrader, int firstItem,
                       String secondTrader, int secondItem){
        super(tradeNumber);
        this.firstTrader = firstTrader;
        this.firstItem = firstItem;
        this.secondTrader = secondTrader;
        this.secondItem = secondItem;
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


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and was originally owned by the Trader at the given
     * index in getTraders(). Iff the specified Trader has no item that he/she is involving in trade and originally
     * owned, store null at the particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    public List<Integer> getItemsOriginal(){
        List<Integer> items = new ArrayList();
        items.add(getFirstTraderItem());
        items.add(getSecondTraderItem());
        return items;
    }
}