package main.java.controller;

import main.java.model.item.ItemNotFoundException;
import main.java.model.item.ItemStorage;
import main.java.presenter.ItemPresenter;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import javax.swing.*;
import java.util.*;

import java.io.IOException;
import java.util.HashMap;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Controller that returns the needed information for GUI client to display for Requests tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class RequestsController {
    private final StorageGateway storageGateway;
    private final ItemStorage itemStorage;
    private final ItemPresenter itemPresenter;
    private final String username;

    /**
     * Initializes a new RequestsController for the given username
     *
     * @param storageGateway gateway for loading and saving information
     * @param itemPresenter
     * @param username username of the user accessing the Requests tab
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public RequestsController(StorageGateway storageGateway, ItemPresenter itemPresenter, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        this.itemPresenter = itemPresenter;
        this.username = username;
        StorageFactory sf = new StorageFactory();
        itemStorage = (ItemStorage) sf.getStorage(storageGateway, StorageEnum.valueOf("ITEM"));
    }

    /**
     * Gets a list containing HashMaps of data of all Trades that the user has requested
     *
     * @return List of Trade datas
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
     * @throws IOException
     */
    public void verifyItem(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.verifyItem(itemID);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }

    /**
     * Rejects the item with given itemID
     *
     * @param itemID ID of the item
     * @throws ItemNotFoundException invalid itemID
     * @throws IOException
     */
    public void rejectItem(int itemID) throws ItemNotFoundException, IOException {
        itemStorage.removeItem(itemID);
        storageGateway.saveStorageData(StorageEnum.valueOf("ITEM"));
    }

    public List<String> getFormattedRequests(){
        List<HashMap<String, String>> requestsList = null;
        List<String> formattedRequestsList = null;
        try {
            requestsList = getRequests();
        } catch (ItemNotFoundException itemNotFoundException) {
            showMessageDialog(null, itemNotFoundException.getStackTrace());
        }
        assert requestsList != null;
        formattedRequestsList = itemPresenter.formatItemsToListView(requestsList);
        return formattedRequestsList;
    }

    public void displayRequests(JTextArea txtArea){
        List<String> formattedRequestsList = getFormattedRequests();
        for (int i = 0; i < formattedRequestsList.size(); i++){
            // text area appends each line of the formatted list
            txtArea.append(formattedRequestsList.get(i));
        }
    }

    public void requestsResponse(JTextField txt, JRadioButton rbtnAcceptRequest, JRadioButton rbtnDenyRequest){
        List<HashMap<String, String>> requestsList = null;
        try {
            requestsList = getRequests();
        } catch (ItemNotFoundException itemNotFoundException) {
            itemNotFoundException.printStackTrace();
        }
        List<String> formattedRequestsList = getFormattedRequests();
        txt.setText(formattedRequestsList.get(0));
        if (requestsList != null) {
            requestsList.remove(requestsList.get(0));
            formattedRequestsList.remove(formattedRequestsList.get(0));
            try {
                if (rbtnAcceptRequest.isSelected()) {
                    verifyItem(Integer.parseInt(requestsList.get(0).get("id")));

                } else if (rbtnDenyRequest.isSelected()) {
                    rejectItem(Integer.parseInt(requestsList.get(0).get("id")));

                } else {
                    requestsList.add(requestsList.get(0));
                    formattedRequestsList.add(formattedRequestsList.get(0));
                    showMessageDialog(null, "Please accept or deny this request!");
                }
            } catch (ItemNotFoundException | IOException exception) {
                showMessageDialog(null, exception.getStackTrace());
            }
        }
    }

}
