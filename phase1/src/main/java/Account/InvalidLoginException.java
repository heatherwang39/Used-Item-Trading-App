package main.java.Account;

/**
 * Exception to be thrown when attempting to create an Account with improper username or password.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class InvalidLoginException extends Exception {

    /**
     * Class Constructor.
     */
    public InvalidLoginException() {
        super("The username and/or password did not match the criteria: Only numbers, letters, dashes, and " +
                "underscores permitted. ");
    }
}
