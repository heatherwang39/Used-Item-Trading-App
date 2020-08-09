package main.java.model.account;

/**
 * Exception to be thrown when attempting to delete a Status that isn't active on an Account.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class StatusNotFoundException extends Exception {

    /**
     * Class Constructor.
     */
    public StatusNotFoundException() {
        super("Attempted to delete a Status that isn't active on the Account.");
    }
}
