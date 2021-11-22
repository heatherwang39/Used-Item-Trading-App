package main.java.model.trade;

/**
 * Exception to be thrown when attempting to make changes to a trade through the wrong account
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class WrongTradeAccountException extends Exception {

    /**
     * Class Constructor
     */
    public WrongTradeAccountException() {
        super("Error: The given account is not associated with this Trade");
    }
}
