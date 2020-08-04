package main.java.model.account;

/**
 * AccountNotFoundException is thrown when an account cannot be found with the given username.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class AccountNotFoundException extends Exception {

    /**
     * Class Constructor
     */
    public AccountNotFoundException() {
        super("An account was not found with the input username.");
    }

}
