package main.java.model.trade;

/**
 * An interface for classes that create for Trade Algorithms
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface TradeAlgorithmCreator {

    /**
     * Constructs the a new instance of the given trade algorithm.
     *
     * @param tradeAlgorithmName The name of the Trade Algorithm
     * @return The trade algorithm with the given name.
     * @throws NoSuchTradeAlgorithmException Exception thrown when no Trade Algorithm has the given name
     */
    TradeAlgorithm getTradeAlgorithm(TradeAlgorithmName tradeAlgorithmName) throws NoSuchTradeAlgorithmException;
}
