package main.java.model.account;

/**
 * Exception to be thrown when attempting to make unsupported changes to a given Account
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class WrongAccountTypeException extends Exception{

    /**
     * Class Constructor.
     */
    public WrongAccountTypeException() {
        super("Error: This operation is not supported by this Account Type.");
    }
}