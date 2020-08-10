package main.java.system;

/**
 * Exception to be thrown when attempting to get a Storage with an invalid type.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class InvalidStorageTypeException extends Exception {

    /**
     * Class Constructor.
     */
    public InvalidStorageTypeException() {
        super("Attempted to get a Storage with an invalid type.");
    }

}
