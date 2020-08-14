package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.StatusNotFoundException;
import main.java.model.account.WrongAccountTypeException;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller that returns the needed information for GUI client to display for Freeze tab
 *
 * @author Fadi Hareth, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class FreezeController {
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;


    /**
     * Class constructor for FreezeController
     *
     * @param storageDepot storageDepot associated with the program
     */
    public FreezeController(StorageDepot storageDepot) {
        storageGateway = storageDepot.getStorageGateway();
        accountStorage = storageDepot.getAccountStorage();
    }

    /**
     * Show all the users that need to be frozen based on threshold and set their status to be frozen
     *
     * @return the frozen users and the frozen reasons
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     *
     */
    public List<List<String>> showAllFrozenUsers() throws AccountNotFoundException, WrongAccountTypeException {
        List<String> frozenUsers = accountStorage.getAccountsWithStatus("FROZEN");
        List<List<String>> allFrozenUsersAndReasons = new ArrayList<>();
        for (String username: frozenUsers) {
            List<String> userAndReasons = new ArrayList<>();
            List<String> reasons = accountStorage.showFreezeReasons(username);
            userAndReasons.add(username);
            userAndReasons.addAll(reasons);
            allFrozenUsersAndReasons.add(userAndReasons);
        }
        return allFrozenUsersAndReasons;
    }

    /**
     * Sets a certain user's status to frozen
     *
     * @param username username of the user
     * @throws IOException file can't be read/written
     * @throws AccountNotFoundException account not found
     * @throws WrongAccountTypeException wrong account type
     */
    public void freezeUser(String username) throws IOException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.createStatus(username, "FROZEN");
        storageGateway.saveStorageData();
    }

    /**
     * Removes the freeze status from a certain user
     *
     * @param username username of the user
     * @throws IOException file cannot be read/written
     * @throws StatusNotFoundException status was not found
     * @throws AccountNotFoundException account was not found
     * @throws WrongAccountTypeException account type doesn't support statuses
     */
    public void unfreezeUser(String username) throws IOException, StatusNotFoundException, AccountNotFoundException, WrongAccountTypeException {
        accountStorage.removeStatus(username, "FROZEN");
        storageGateway.saveStorageData();
    }


}
