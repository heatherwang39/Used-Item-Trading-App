package main.java.model.meeting;

/**
 * Exception to be thrown when no such meeting corresponds to the meeting number
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */

public class MeetingIDException extends Exception {

    /**
     * Class Constructor
     */
    public MeetingIDException() {
        super("A Meeting was not found with the input Meeting ID.");
    }
}
