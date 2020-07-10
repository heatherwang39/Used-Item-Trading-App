package main.java;

/**
 * Exception to be thrown when attempting to make changes to a trade through the wrong account
 * @author Charles Wang
 * @version %I%, %G%
 * @since Phase 1
 */
public class UserNotFoundException extends Exception {

    /**
     * Class Constructor
     */
    public UserNotFoundException() { super();
    }

}