package main.java.model.status;

/**
 * Exception to be thrown when attempting to create a Status with an improper type.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class ImproperStatusTypeException extends Exception {

    /**
     * Class Constructor.
     */
    public ImproperStatusTypeException() {
        super("Attempted to create a Status with an improper type.");
    }
}
