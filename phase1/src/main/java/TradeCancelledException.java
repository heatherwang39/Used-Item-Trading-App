package main.java;

/**
 * Exception to be thrown when attempting to make changes to a meeting that will result in the trade being cancelled.
 * Also thrown if the trade is already cancelled when trying to make these changes.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class TradeCancelledException extends Exception {

    /**
     * Class Constructor
     */
    public TradeCancelledException() {
        super();
    }

}