package main.java.controller;

/**
 * This Exception is thrown when the given user has already been added to the Trade
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class UserAlreadyAddedException extends Exception {

    /**
     * Class Constructor
     */
    public UserAlreadyAddedException() {
        super("The given user has already been added to this trade");
    }
}
