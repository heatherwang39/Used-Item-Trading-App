package main.java.model.account;

/**
 * Exception to be thrown when attempting to create an Account with an email address already in use.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class EmailAddressInUseException extends Exception {

    /**
     * Class Constructor.
     */
    public EmailAddressInUseException() {
        super("The input email address is already in use.");
    }
}