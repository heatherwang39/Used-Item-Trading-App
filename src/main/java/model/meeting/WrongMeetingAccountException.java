package main.java.model.meeting;

/**
 * Exception to be thrown when attempting to make changes to a meeting through the wrong account
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class WrongMeetingAccountException extends Exception {

    /**
     * Class Constructor
     */
    public WrongMeetingAccountException() {
        super("Error: The given account is not associated with this Meeting");
    }
}
