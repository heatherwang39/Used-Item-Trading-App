package main.java;

import java.io.Serializable;
import java.util.List;

abstract class Trade implements Serializable {
    private int tradeNumber;
    private int status;

    /** Initialize a new instance of Trade. The default status of the trade will be set to 0,
     * and the Trade will be given a unique tradeNumber.
     *
     */
    public Trade(int tradeNumber){
        this.tradeNumber = tradeNumber;
        status = 0;
    }


    /**
     * Return the trade number of the main.java.Trade object
     *
     * @return the trade number of the main.java.Trade object
     */
    public int getTradeNumber(){
        return tradeNumber;
    }

    /**
     * Return the status of the main.java.Trade Object.
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade is ongoing
     * 2 represents that the trade has been completed
     *
     * @return the status of the main.java.Trade Object.
     */
    public int getStatus(){
        return status;
    }


    /**
     * Changes the status of the main.java.Trade object. Iff the change was successfully made, return True.
     *
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade is ongoing
     * 2 represents that the trade has been completed
     *
     * @param status The new status of the main.java.Trade
     * @return A boolean representing whether or not the change was made
     */
    public boolean setStatus(int status){
        if(this.status == status){
            return false;
        }
        this.status = status;
        return true;
    }

    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    abstract boolean isPermanent();


    /** Returns whether or not the Trade is one-way. Iff the Trade is one-way, return true.
     *
     * @return whether the Trade is one-way
     */
    abstract boolean isOneWay();


    /** Returns a List of Traders (i.e., their usernames) involved in this trade
     *
     * @return Usernames of Traders involved in this trade
     */
    abstract List<String> getTraders();


    /** Returns a List of Items (i.e., their ids) in an order based on the item's owner before the trade and the order
     *  of the Traders that getTraders() returns.
     *
     * @return The list of items involved in this trade
     */
    abstract List<Integer> getItems();
}
