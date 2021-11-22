package main.java.model.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A Trade Algorithm that cycles the traders' items
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class CycleTradeAlgorithm implements TradeAlgorithm, Serializable {

    /**
     * Return the new arrangement of items based on the original items
     *
     * @param originalItems The items based on their original owners
     * @return A ordered list of Item IDs corresponding to what happens after the first transaction in the trade
     */
    public List<Integer> getExchangedItems(List<Integer> originalItems){
        int max_i = originalItems.size();
        List<Integer> exchange = new ArrayList<>();
        int i = 0;
        while(i < max_i){
            if(i == max_i - 1){
                exchange.add(originalItems.get(0));
            }
            else{
            exchange.add(originalItems.get(i + 1));
            }
            i++;
        }
        return exchange;
    }

    /** Return the name of the Trade Algorithm
     *
     * @return The Trade Algorithm Name
     */
    public String getTradeAlgorithmName() {
        return "CYCLE";
    }
}
