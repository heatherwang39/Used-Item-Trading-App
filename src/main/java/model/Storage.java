package main.java.model;

/**
 * Interface representing a use case Storage class.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface Storage {

    /**
     * This generates a new structure matching the Storage's internal data
     * @return Empty storage data
     */
    Object getNewStorageData();

    /**
     * This sets the Storage's data
     * @param storageData data
     */
    void setStorageData(Object storageData);
}
