package main.java.model.account;

/**
 * Exception to be thrown when attempting to create an Account with improper email address.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class InvalidEmailAddressException extends Exception {

    /**
     * Class Constructor.
     */
    public InvalidEmailAddressException() {
        super("The email address did not match the criteria for a valid email address.");
    }
}
