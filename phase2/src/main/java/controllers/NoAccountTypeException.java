package main.java.controllers;

/**
 * An exception thrown when the controller for the given Account Type hasn't been implemented yet
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class NoAccountTypeException extends Exception{

    /**
     * Class Constructor
     */
    public NoAccountTypeException() {
        super("Error, the controller for this type of account hasn't been implemented yet. " +
                "Please notify the developers.");
    }
}
