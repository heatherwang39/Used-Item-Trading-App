package main.java.model.trade;

/**
 * Exception to be thrown when an item that is going to be involved in a newly initialized
 * trade is already in another active trade
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class ItemAlreadyInActiveTradeException extends Exception{


    /**
     * Class Constructor
     */
    public ItemAlreadyInActiveTradeException() {
        super("Error: One of the Items in this Trade is already in another active Trade");
    }
}
