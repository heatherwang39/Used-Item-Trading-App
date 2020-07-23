package main.java.Transactions.Trade;

/**
 * Exception to be thrown when trying to
 * 1) suggest/set a meeting that isn't suppose to happen.
 * 2) accept a meeting when a meeting hasn't even been suggested.
 * 3) confirming a meeting that hasn't been accepted.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class NoMeetingException extends Exception {

    /**
     * Class Constructor
     */
    public NoMeetingException() {
        super();
    }

}