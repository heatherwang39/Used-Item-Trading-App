package main.java.controller;

import main.java.model.account.*;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

import java.io.IOException;

/**
 * A Controller for the Main, Register, and Login Tab
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class LoginController {

    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public LoginController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory storageFactory = new StorageFactory();
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
    }

     /** Check if you can login with the given data. If you can, return True
     *
     * @param username The username of the account
     * @param password The password of the account
     * @return Whether you can login
     */
    public String login(String username, String password) throws AccountNotFoundException {
        if (accountStorage.tryLogin(username, password)) {
            return accountStorage.getType(username);
        }
        return "NULL";
    }

    /** Attempt to register an account. If no exceptions are thrown, registration was successful.
     *
     * @param username The username of the account you're trying to register
     * @param password The password of the account you're trying to register
     * @param emailAddress The email associated to the account you're trying to register
     * @throws InvalidUsernameException  username doesn't match regex
     * @throws InvalidPasswordException  password doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws InvalidEmailAddressException Email is Invalid
     * @throws EmailAddressInUseException Email is in use
     */
    public void register(String username, String password, String emailAddress) throws
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException, IOException, InvalidUsernameException, InvalidPasswordException {
        accountStorage.createUser(username, password, emailAddress);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }
}
