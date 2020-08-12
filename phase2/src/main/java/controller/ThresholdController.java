package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.NegativeThresholdException;
import main.java.model.account.WrongAccountTypeException;
import main.java.system.FileReadWriter;
import main.java.system.StorageEnum;
import main.java.system.StorageFactory;
import main.java.system.StorageGateway;

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
    private final String configKey = "THRESHOLDS";

    /**
     * Initializes a new ThresholdController
     *
     * @param storageGateway gateway for loading and saving information
     */

    public ThresholdController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory sf = new StorageFactory();
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.ACCOUNT);
        path = storageGateway.getPath();
        filename = storageGateway.getFilenameMap().get(configKey);
    }

    /** Set all thresholds for all user account from the data in text file
     *
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws AccountNotFoundException Thrown when no account has the given username
     */
    public void setAllThresholdsFromTextFile() throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        FileReadWriter frw = new FileReadWriter(path+filename);
        HashMap<String, String> thresholdsData = frw.readAsMapFromTextFile();
        int borrowThreshold = Integer.parseInt(thresholdsData.get("borrowThreshold"));
        int incompleteThreshold = Integer.parseInt(thresholdsData.get("incompleteThreshold"));
        int weeklyThreshold = Integer.parseInt(thresholdsData.get("weeklyThreshold"));
        int gildedThreshold = Integer.parseInt(thresholdsData.get("gildedThreshold"));
        accountStorage.setAllThresholds(borrowThreshold,incompleteThreshold,weeklyThreshold,gildedThreshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set borrowing threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setBorrowingThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllBorrowThresholds(threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set incomplete trades threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setIncompleteThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllIncompleteThresholds(threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set weekly trades threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setWeeklyThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllWeeklyThresholds(threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set gilded threshold for All Users
     *
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setGildedThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException, IOException {
        accountStorage.setAllGildedThresholds(threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
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
     */
    public void setBorrowingThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setBorrowThreshold(username,threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set incomplete trades threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setIncompleteThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setIncompleteThreshold(username,threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set weekly trades threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setWeeklyThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setWeeklyThreshold(username,threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }

    /**
     * Set gilded threshold for a given user
     *
     * @param username the username of the user whose threshold to be set
     * @param threshold the number that set the threshold to
     * @throws AccountNotFoundException Thrown when no account has the given username
     * @throws WrongAccountTypeException Thrown if the account doesn't have a threshold associated with it
     * @throws NegativeThresholdException Thrown if the suggested threshold is negative
     */
    public void setGildedThresholdForUser(String username, int threshold) throws AccountNotFoundException, NegativeThresholdException, WrongAccountTypeException, IOException {
        accountStorage.setGildedThreshold(username,threshold);
        storageGateway.saveStorageData(StorageEnum.ACCOUNT);
    }


}