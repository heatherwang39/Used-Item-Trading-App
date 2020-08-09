package main.java.controller;

import main.java.model.status.InvalidStatusTypeException;
import main.java.model.status.StatusNotFoundException;
import main.java.model.status.StatusStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

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
    private final StatusStorage statusStorage;

    /** Class constructor
     *
     * @param username username of the user
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public StatusController(String username, StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.username = username;
        StorageFactory storageFactory = new StorageFactory();
        this.storageGateway = storageGateway;
        this.statusStorage = (StatusStorage)storageFactory.getStorage(storageGateway, StorageEnum.STATUS);
    }

    /** Get all status that the user has
     *
     * @return all status that the user has
     */
    public List<String> getStatuses() {
        return statusStorage.getAccountStatusStrings(username);
    }

    /** Set the away status for current account
     *
     * @throws InvalidStatusTypeException when the type of status is invalid
     */
    public void setAwayStatus() throws InvalidStatusTypeException, IOException {
        statusStorage.createStatus(username,"AWAY");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }

    /** Remove the away status
     *
     * @throws StatusNotFoundException cannot find the away status in current account
     */
    public void removeAwayStatus() throws StatusNotFoundException, IOException {
        statusStorage.removeStatus(username,"AWAY");
        storageGateway.saveStorageData(StorageEnum.STATUS);
    }




}
