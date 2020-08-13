package main.java.controller;

/**
 * This Exception is thrown when not enough Traders in suggested in the given Random trade
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class TooFewTradersException extends Exception {

    /**
     * Class Constructor
     */
    public TooFewTradersException() {
        super("Too few traders have been suggested for this Random Trade.");
    }
}
