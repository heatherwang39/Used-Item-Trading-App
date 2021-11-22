package main.java.model.trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A Trade Algorithm that randomly distributes the traders' items
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class RandomTradeAlgorithm implements TradeAlgorithm, Serializable {
    private final int seed;


    public RandomTradeAlgorithm(){
        seed = (int)(Math.random()*100);
    }


    /**
     * Return the new arrangement of items based on the original items
     *
     * @param originalItems The items based on their original owners
     * @return A ordered list of Item IDs corresponding to what happens after the first transaction in the trade
     */
    public List<Integer> getExchangedItems(List<Integer> originalItems){
        List<Integer> exchange = new ArrayList<>(originalItems);
        Collections.shuffle(exchange, new Random(seed));
        return exchange;
    }

    /** Return the name of the Trade Algorithm
     *
     * @return The Trade Algorithm Name
     */
    public String getTradeAlgorithmName() {
        return "RANDOM";
    }
}
