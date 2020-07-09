package main.java;

/**
 * Exception to be thrown by the TwoPersonMeeting class when attempting to make changes to a meeting that will result
 * in the trade being cancelled
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