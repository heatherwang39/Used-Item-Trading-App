package main.java;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a one-way permanent trade in main.java.Transactions.Trade system. Once the items have been given to the recipient,
 * they will not be returned. As such, these trades require only one meeting.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class OneWayPermanentTrade extends OneWayTrade implements Serializable {

    /** Initializes an instance of main.java.OneWayPermanentTrade based on the given parameters
     *
     * @param tradeNumber The trade number of the trade
     * @param sender The trader (their username) that sent the item
     * @param receiver The trader (their username) that received the item
     * @param item The item (its ID) that was traded from the sender to the receiver
     */
    public OneWayPermanentTrade(int tradeNumber, String sender, String receiver, int item){
        super(tradeNumber, sender, receiver, item);

        newMeeting(getTraders());
    }



    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    public boolean isPermanent(){
        return true;
    }


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and will be own by the Trader at the given
     * index in getTraders() at the end of the trade. Iff the specified Trader will not receive an item that at the end
     * of the trade, store null at that particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    public List<Integer> getItemsFinal(){
        List<Integer> items = new ArrayList();
        items.add(null);
        items.add(getItem());
        return items;
    }
}