package main.java.model.trade;

/**
 * Exception to be thrown when no trade has the given trade number
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class TradeNumberException extends Exception {

    /**
     * Class Constructor
     */
    public TradeNumberException() {
        super("A Trade was not found with the input Trade Number.");
    }

}
