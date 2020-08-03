package main.java.model.status;

/**
 * Exception to be thrown when attempting to create a Status with an invalid type.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class InvalidStatusTypeException extends Exception {

    /**
     * Class Constructor.
     */
    public InvalidStatusTypeException() {
        super("Attempted to create a Status with an invalid type.");
    }
}
