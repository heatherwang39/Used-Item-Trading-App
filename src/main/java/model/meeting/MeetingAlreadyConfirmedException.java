package main.java.model.meeting;

/**
 * Exception to be thrown when trying to cancel an already confirmed meeting
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class MeetingAlreadyConfirmedException extends Exception{

    /**
     * Class Constructor
     */
    public MeetingAlreadyConfirmedException() {
        super("Error: The given meeting is already confirmed and thus won't be cancelled");
    }
}