package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.StatusNotFoundException;
import main.java.model.account.WrongAccountTypeException;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.List;

/**
 * A Controller to set status and get status
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class GildedController {

    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;

    /** Class constructor
     *
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public GildedController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        StorageFactory storageFactory = new StorageFactory();
        this.storageGateway = storageGateway;
        accountStorage = (AccountStorage)storageFactory.getStorage(storageGateway,StorageEnum.ACCOUNT);
    }

    /** Get all gilded Users who have completed more than 20 trades and set their status to be gilded in the same time
     *
     * @return the usernames of all gilded Users
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */
    public List<String> getAllGildedUsers() throws AccountNotFoundException, WrongAccountTypeException {
        List<String> gildedUsers = accountStorage.getAccountsWithStatus("GILDED");

        return gildedUsers;
    }

    /** Add the gilded status for a certain user
     *
     * @param username the username of the user
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */
    public void setGildedStatus(String username) throws IOException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.createStatus(username,"GILDED");
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /** Remove the gilded status for a certain user
     *
     * @param username the username of the user
     * @throws IOException file cannot be read/written
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     *
     */
    public void removeGildedStatus(String username) throws IOException, StatusNotFoundException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.removeStatus(username,"GILDED");
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }


}
