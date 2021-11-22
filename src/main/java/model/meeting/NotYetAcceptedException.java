package main.java.model.meeting;

/**
 * Exception to be thrown when trying to confirming a meeting that hasn't been accepted yet.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class NotYetAcceptedException extends Exception {

    /**
     * Class Constructor
     */
    public NotYetAcceptedException() {
        super("Error: The meeting hasn't been accepted, so it cannot be confirmed.");
    }
}
