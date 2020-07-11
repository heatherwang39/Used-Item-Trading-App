package main.java;

/**
 * Exception to be thrown when attempting to make changes to a meeting in a trade that is already cancelled.
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