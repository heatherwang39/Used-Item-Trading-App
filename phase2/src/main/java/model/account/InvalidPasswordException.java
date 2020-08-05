package main.java.model.account;

/**
 * Exception to be thrown when attempting to create an Account with improper password.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class InvalidPasswordException extends Exception{

    /**
     * Class Constructor.
     */
    public InvalidPasswordException() {
        super("This password is not valid: alphanumeric characters, at least 1 letter and number, at least 8 characters.");
    }
}
