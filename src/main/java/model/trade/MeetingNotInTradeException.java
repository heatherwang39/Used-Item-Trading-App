package main.java.model.trade;

/**
 * Exception to be thrown when the specified trade doesn't have the specified meeting in it.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class MeetingNotInTradeException extends Exception{

    /**
     * Class Constructor
     */
    public MeetingNotInTradeException() {
        super("The given Trade is not associated with the given Meeting");
    }
}
