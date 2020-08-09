package main.java.controller;

import main.java.model.account.AccountNotFoundException;
import main.java.model.account.AccountStorage;
import main.java.model.account.NegativeThresholdException;
import main.java.model.account.WrongAccountTypeException;
import main.java.model.status.StatusStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;

/**
 * Controller that returns the needed information for GUI client to display for Threshold tab
 *
 * @author Charles Wang
 * @version %I%, %G%
 * @since Phase 2
 */
public class ThresholdController {
    private final StorageGateway storageGateway;
    private final AccountStorage accountStorage;


    /**
     * Initializes a new ThresholdController
     *
     * @param storageGateway gateway for loading and saving information
     */

    public ThresholdController(StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory sf = new StorageFactory();
        accountStorage = (AccountStorage) sf.getStorage(storageGateway, StorageEnum.ACCOUNT);

    }


    public void setBorrowingThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException {
        accountStorage.setAllBorrowThresholds(threshold);
    }

    public void setIncompleteThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException {
        accountStorage.setAllIncompleteThresholds(threshold);
    }

    public void setWeeklyThreshold(int threshold) throws AccountNotFoundException, WrongAccountTypeException, NegativeThresholdException {
        accountStorage.setAllWeeklyThresholds(threshold);
    }

}