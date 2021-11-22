package main.java.model.account;

/**
 * Exception to be thrown when attempting to create an Account with a username already in use.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class UsernameInUseException extends Exception {

    /**
     * Class Constructor.
     */
    public UsernameInUseException() {
        super("The input username is already in use.");
    }
}