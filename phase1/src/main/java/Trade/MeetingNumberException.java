package main.java.Trade;

/**
 * Exception to be thrown when no such meeting corresponds to the meeting number.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class MeetingNumberException extends Exception {

    /**
     * Class Constructor.
     */
    public MeetingNumberException() {
        super("No meeting exists under meeting number.");
    }
}
