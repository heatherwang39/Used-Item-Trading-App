package main.java.controller;

import main.java.model.account.*;
import main.java.model.item.ItemStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Controller that returns the needed information for GUI client to display for Add Admin tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class AddAdminController {
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;
    private final String username;

    /**
     * Initializes a new AddAdminController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param username username of the user accessing the AddAdmin tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public AddAdminController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ACCOUNT"));
    }

    /**
     * Creates an AdminAccount if the credentials are valid.
     *
     * @param username input username
     * @param password input password
     * @param emailAddress input email
     * @throws InvalidUsernameException  username doesn't match regex
     * @throws InvalidPasswordException  password doesn't match regex
     * @throws InvalidEmailAddressException email doesn't match regex
     * @throws UsernameInUseException username is in use
     * @throws EmailAddressInUseException email address is in use
     * @throws IOException
     */
    public void addAdmin(String username, String password, String emailAddress) throws IOException,
            InvalidUsernameException, InvalidPasswordException, InvalidEmailAddressException, EmailAddressInUseException, UsernameInUseException {
        accountStorage.createAdmin(username, password, emailAddress);
        storageGateway.saveStorageData(StorageEnum.valueOf("ACCOUNT"));
    }

}
