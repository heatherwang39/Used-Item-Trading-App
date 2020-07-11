package main.java;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a two-way temporary trade in main.java.Trade system. The items that have been exchanged between the two
 * traders will be returned. As such, these trades require two meetings.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class TwoWayTemporaryTrade extends TwoWayTrade implements Serializable {

    /** Initializes an instance of TwoWayTemporaryTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayTemporaryTrade(int tradeNumber, String firstTrader, int firstItem,
                                String secondTrader, int secondItem) {

        super(tradeNumber, firstTrader, firstItem, secondTrader, secondItem);

        newMeeting(getTraders());
        newMeeting(getTraders());
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    public boolean isPermanent(){
        return false;
    }


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and will be own by the Trader at the given
     * index in getTraders() at the end of the trade. Iff the specified Trader will not receive an item that at the end
     * of the trade, store null at that particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    public List<Integer> getItemsFinal(){
        return getItemsOriginal();
    }
}
