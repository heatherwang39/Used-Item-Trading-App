package main.java.controllers;

import main.java.model.account.*;

/**
 * A Controller for the Login Screen
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class LoginController {
    private AccountStorage as;
    //private StorageFactory sf;

    /** Check if you can login with the given data. If you can, return True
     *
     * @param username The username of the account
     * @param password The password of the account
     * @return Whether you can login
     */
    boolean login(String username, String password){
        return as.tryLogin(username, password);
    }

    /** Attempt to register an account. If no exceptions are thrown, registration was successful.
     *
     * @param username The username of the account you're trying to register
     * @param password The password of the account you're trying to register
     * @param emailAddress The email associated to the account you're trying to register
     * @throws InvalidLoginException login doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws InvalidEmailAddressException Email is Invalid
     * @throws EmailAddressInUseException Email is in use
     */
    void register(String username, String password, String emailAddress) throws InvalidLoginException,
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException{
        as.createUser(username, password, emailAddress);
    }
}
