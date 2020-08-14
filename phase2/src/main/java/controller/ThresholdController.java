package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.NegativeThresholdException;
import main.java.model.account.WrongAccountTypeException;
import main.java.system.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * Controller that returns the needed information for GUI client to display for Threshold tab
 *
 * @author Charles Wang, Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class ThresholdController {
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;
    private final String path;
    private final String filename;

    /**
     * Initializes a new ThresholdController
     *
     * @param storageDepot storageDepot associated with the program
     */

    public ThresholdController(StorageDepot storageDepot) {
        storageGateway = storageDepot.getStorageGateway();
        accountStorage = storageDepot.getAccountStorage();
        path = storageGateway.getPath();
        String configKey = "THRESHOLDS";
        filename = storageGateway.getFilenameMap().get(configKey);
    }


    /** Set all thresholds for all user account from the data in text file
     *
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws IOException file cannot be read/written
     */
    public void setAllThresholdsFromTextFile() throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        FileReadWriter frw = new FileReadWriter(path+filename);
        HashMap<String, String> thresholdsData = frw.readAsMapFromTextFile();
        int borrowThreshold = Integer.parseInt(thresholdsData.get("borrowThreshold"));
        int incompleteThreshold = Integer.parseInt(thresholdsData.get("incompleteThreshold"));
        int weeklyThreshold = Integer.parseInt(thresholdsData.get("weeklyThreshold"));
        int gildedThreshold = Integer.parseInt(thresholdsData.get("gildedThreshold"));
        accountStorage.setAllThresholds(borrowThreshold,incompleteThreshold,weeklyThreshold,gildedThreshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set borrowing threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setBorrowingThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllBorrowThresholds(threshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set incomplete trades threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setIncompleteThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllIncompleteThresholds(threshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set weekly trades threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setWeeklyThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllWeeklyThresholds(threshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set gilded threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setGildedThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllGildedThresholds(threshold);
        storageGateway.saveStorageData();
    }

    /** Return all current thresholds of the given account
     *
     * @param username The username of the account you're interested in
     * @return A HashMap with key is the threshold's name and value is threshold's number
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public HashMap<String, Integer> getCurrentThresholds(String username) throws WrongAccountTypeException, AccountNotFoundException {
        return accountStorage.getCurrentThresholds(username);
    }

    /**
     * Set borrowing threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setBorrowingThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setBorrowThreshold(username,threshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set incomplete trades threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setIncompleteThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setIncompleteThreshold(username,threshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set weekly trades threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setWeeklyThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setWeeklyThreshold(username,threshold);
        storageGateway.saveStorageData();
    }

    /**
     * Set gilded threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws IOException file cannot be read/written
     */
    public void setGildedThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setGildedThreshold(username,threshold);
        storageGateway.saveStorageData();
    }
}