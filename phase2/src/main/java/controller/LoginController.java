package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemStorage;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import javax.swing.*;
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
    private final StatusStorage statusStorage;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public LoginController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory storageFactory = new StorageFactory();
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
        statusStorage = (StatusStorage) storageFactory.getStorage(storageGateway, StorageEnum.STATUS);
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
     * @throws InvalidStatusTypeException Status is Invalid
     */
    public void register(String username, String password, String emailAddress) throws
            UsernameInUseException, InvalidEmailAddressException, EmailAddressInUseException, IOException, InvalidStatusTypeException, InvalidUsernameException, InvalidPasswordException {
        accountStorage.createUser(username, password, emailAddress);
        statusStorage.createStatus(username,"NEW");
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }
}
