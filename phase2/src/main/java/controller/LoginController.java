package main.java.controller;

import main.java.model.account.*;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusStorage;
import main.java.system2.InvalidStorageTypeException;
import main.java.system2.StorageFactory;

import java.io.IOException;

/**
 * A Controller for the Login Screen
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class LoginController {
    private StorageFactory sf;
    private AccountStorage as;
    private StatusStorage ss;

    /** Class constructor
     *
     * @param storageFactory The storage factory that this controller uses
     */
    public LoginController(StorageFactory storageFactory){
        sf = storageFactory;
        try{
            as = (AccountStorage) sf.getStorage("ACCOUNT");
            ss = (StatusStorage) sf.getStorage("STATUS");
        }
        catch(IOException | InvalidStorageTypeException | ClassNotFoundException ignored){}
    }

    /** Check if you can login with the given data. If you can, return True
     *
     * @param username The username of the account
     * @param password The password of the account
     * @return Whether you can login
     */
    public boolean login(String username, String password) throws AccountNotFoundException{
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
     * @throws InvalidStatusTypeException Status is Invalid
     */
    public void register(String username, String password, String emailAddress) throws InvalidLoginException,
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException, InvalidStatusTypeException {
        as.createUser(username, password, emailAddress);
        ss.createStatus(username,"NEW");
    }
}
