package main.java.model.trade;

import java.util.List;


/**
 * An concrete class in the Trade system representing a temporary trade (i.e., the items will be returned)
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class TemporaryTrade extends Trade {


    /** Initialize a new instance of TemporaryTrade.
     *
     * @param tradeNumber The unique TradeNumber associated with this trade
     * @param tradeAlgorithm The algorithm that determines how the trade functions
     * @param traders The traders involved in this trade
     * @param items The items involved in this trade in the order of their owners (in traders)
     */
    public TemporaryTrade(int tradeNumber, TradeAlgorithm tradeAlgorithm, List<String> traders, List<Integer> items){
        super(tradeNumber, tradeAlgorithm, traders, items);
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
