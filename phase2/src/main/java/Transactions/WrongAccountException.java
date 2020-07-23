package main.java.Transactions;

/**
 * Exception to be thrown when attempting to make changes to a trade/meeting through the wrong account
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class WrongAccountException extends Exception {

    /**
     * Class Constructor
     */
    public WrongAccountException() {
        super();
    }

}