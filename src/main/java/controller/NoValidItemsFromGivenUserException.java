package main.java.controller;

/**
 * This Exception is thrown when the given user has no valid items that they can involve in the given trade.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class NoValidItemsFromGivenUserException extends Exception{

    /**
     * Class Constructor
     */
    public NoValidItemsFromGivenUserException() {
        super("The given user has no valid items to involve in this trade.");
    }
}
