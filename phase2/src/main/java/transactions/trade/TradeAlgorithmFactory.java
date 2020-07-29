package main.java.transactions.trade;

/**
 * A Factory for Trade Algorithms
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class TradeAlgorithmFactory {

    /**
     *
     * @param tradeAlgorithmName The name of the Trade Algorithm
     * @return The trade algorithm with the given name.
     * @throws NoSuchTradeAlgorithmException Exception thrown when no Trade Algorithm has the given name
     */
    public TradeAlgorithm getTradeAlgorithm(TradeAlgorithmName tradeAlgorithmName) throws NoSuchTradeAlgorithmException{
        TradeAlgorithm ta;
        switch(tradeAlgorithmName) {
            case CYCLE:
                return new CycleTradeAlgorithm();
            //break;
            case RANDOM:
                return new RandomTradeAlgorithm();
            //break;
        }
            throw new NoSuchTradeAlgorithmException();
    }
}
