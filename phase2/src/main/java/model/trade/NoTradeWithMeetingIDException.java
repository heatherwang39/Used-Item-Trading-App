package main.java.model.trade;

/**
 * Exception to be thrown when no trade has a meeting with the given meeting ID
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class NoTradeWithMeetingIDException extends Exception{

    /**
     * Class Constructor
     */
    public NoTradeWithMeetingIDException() {
        super("No Trade was found with the given Meeting ID");
    }
}
