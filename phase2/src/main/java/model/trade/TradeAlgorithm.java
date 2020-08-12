package main.java.model.trade;

import java.util.List;

/**
 * An Interface for the Trade Algorithms
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface TradeAlgorithm {


    /**
     * Return the new arrangement of items based on the original items
     *
     * @param originalItems The items based on their original owners
     * @return A ordered list of Item IDs corresponding to what happens after the first transaction in the trade
     */
    List<Integer> getExchangedItems(List<Integer> originalItems);


    /** Return the name of the Trade Algorithm
     *
     * @return The Trade Algorithm Name
     */
    String getTradeAlgorithmName();
}