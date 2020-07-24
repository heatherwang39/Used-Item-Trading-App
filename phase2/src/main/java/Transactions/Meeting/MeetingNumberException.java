package main.java.Transactions.Meeting;

/**
 * Exception to be thrown when no such meeting corresponds to the meeting number
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class MeetingNumberException extends Exception {

    /**
     * Class Constructor
     */
    public MeetingNumberException() {
        super("A Meeting was not found with the input Meeting ID.");
    }
}
