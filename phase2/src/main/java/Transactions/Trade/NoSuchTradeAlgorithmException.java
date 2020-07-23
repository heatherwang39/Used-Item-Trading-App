package main.java.Transactions.Trade;

/**
 * Exception to be thrown when attempting to use a Trade Algorithm that doesn't exist.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class NoSuchTradeAlgorithmException extends Exception{

    /**
     * Class Constructor
     */
    public NoSuchTradeAlgorithmException() {
        super();
    }

}
