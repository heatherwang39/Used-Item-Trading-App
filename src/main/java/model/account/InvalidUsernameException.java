package main.java.model.account;

/**
 * Exception to be thrown when attempting to create an Account with improper username.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class InvalidUsernameException extends Exception{

    /**
     * Class Constructor.
     */
    public InvalidUsernameException() {
        super("This username is not valid: at least 1 alphanumeric character(s) separated by single underscores or dashes");
    }
}
