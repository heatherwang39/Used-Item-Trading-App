package main.java.controller;

import main.java.model.account.*;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;

import java.io.IOException;

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


    /**
     * Initializes a new AddAdminController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     */
    public AddAdminController(StorageDepot storageDepot) {
        storageGateway = storageDepot.getStorageGateway();
        accountStorage = storageDepot.getAccountStorage();
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
     * @throws IOException class cannot be written
     */
    public void addAdmin(String username, String password, String emailAddress) throws IOException,
            InvalidUsernameException, InvalidPasswordException, InvalidEmailAddressException, EmailAddressInUseException, UsernameInUseException {
        accountStorage.createAdmin(username, password, emailAddress);
        storageGateway.saveStorageData();
    }

}
