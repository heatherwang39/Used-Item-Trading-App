package main.java.controller;

/**
 * An exception to be thrown when a suggested recipient is not found
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class RecipientNotFoundException extends Exception {

    /**
     * Class Constructor
     */
    public RecipientNotFoundException(){
        super("A given recipient was not found.");
    }
}
