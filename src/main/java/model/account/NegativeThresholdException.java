package main.java.model.account;

/**
 * Exception to be thrown when attempting to set a Threshold to be a negative number
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class NegativeThresholdException extends Exception {

    /**
     * Class Constructor.
     */
    public NegativeThresholdException() {
        super("Error: A user's threshold should not be negative.");
    }
}
