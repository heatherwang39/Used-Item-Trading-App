package main.java;

/**
 * Exception to be thrown when attempting to create an Account with improper email address
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class InvalidEmailException extends Exception {

    /**
     * Class Constructor
     */
    public InvalidEmailException() {
        super();
    }
}
