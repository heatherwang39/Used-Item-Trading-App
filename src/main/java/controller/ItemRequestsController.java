package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.presenter.ItemPresenter;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;

import java.util.*;

import java.io.IOException;

/**
 * Controller that returns the needed information for GUI client to display for Requests tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class ItemRequestsController {
    private final StorageGateway storageGateway;
    private final ItemStorage itemStorage;
    private final ItemPresenter itemPresenter;

    /**
     * Initializes a new ItemRequestsController for the given username
     *
     * @param storageDepot storageDepot associated with the program
     * @param itemPresenter presenter class for prepping items for gui
     */
    public ItemRequestsController(StorageDepot storageDepot, ItemPresenter itemPresenter) {
        storageGateway = storageDepot.getStorageGateway();
        this.itemPresenter = itemPresenter;
        itemStorage = storageDepot.getItemStorage();
    }

    /**
     * Gets a list containing HashMaps of data of all Trades that the user has requested
     *
     * @return List of Trade data
     * @throws ItemNotFoundException An item not in the system was inputted
     */
    public List<HashMap<String, String>> getRequests() throws ItemNotFoundException {
        return itemStorage.getUnverifiedItemsData();
    }

    /**
     * Verifies the item with given itemID
     *
     * @param itemID ID of the item
     * @throws ItemNotFoundException invalid itemID
     * @throws IOException file cannot be read/written
     */
    public void verifyItem(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.verifyItem(itemID);
        storageGateway.saveStorageData();
    }

    /**
     * Rejects the item with given itemID
     *
     * @param itemID ID of the item
     * @throws ItemNotFoundException invalid itemID
     * @throws IOException file cannot be read/written
     */
    public void rejectItem(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.removeItem(itemID);
        storageGateway.saveStorageData();
    }

    /**
     * Get requests in formatted presenter string
     * @return Presenter view of requests
     * @throws ItemNotFoundException item was not found
     */
    public List<String> getFormattedRequests() throws ItemNotFoundException {
        return itemPresenter.formatItemsToListView(getRequests());
    }

    /**
     * Get formatted string for requests box
     * @return requests box string
     * @throws ItemNotFoundException item was not found
     */
    public String getRequestsString() throws ItemNotFoundException {
        StringBuilder string = new StringBuilder();
        for (String s : getFormattedRequests()) {
            string.append(s);
        }
        return string.toString();
    }

}
