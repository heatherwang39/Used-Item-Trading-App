package main.java;

/**
 * Exception to be thrown when attempting to create an Account with an email already in use
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class EmailInUseException extends Exception {

    /**
     * Class Constructor
     */
    public EmailInUseException() {
        super();
    }
}