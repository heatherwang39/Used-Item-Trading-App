package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.StatusNotFoundException;
import main.java.model.account.WrongAccountTypeException;
import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusStorage;
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
public class StatusController {
    private String username;
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;

    /** Class constructor
     *
     * @param username username of the user
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public StatusController(String username, StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.username = username;
        StorageFactory storageFactory = new StorageFactory();
        this.storageGateway = storageGateway;
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
    }

    /** Get all status that the user has
     *
     * @return all status that the user has
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */
    public List<String> getStatuses() throws AccountNotFoundException, WrongAccountTypeException {
        return accountStorage.getAccountStatuses(username);
    }

    /** Set the away status for current account
     *
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     */
    public void setAwayStatus() throws IOException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.createStatus(username,"AWAY");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }

    /** Remove the away status
     *
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws StatusNotFoundException when no Status with given type could be found
     */
    public void removeAwayStatus() throws IOException, StatusNotFoundException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.removeStatus(username,"AWAY");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }


}
