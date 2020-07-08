package main.java;

/**
 * Exception to be thrown when attempting to make changes to a trade that is or will soon be cancelled
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