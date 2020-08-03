package main.java.system2;

import main.java.model.Storage;
import main.java.model.account.AccountStorage;
import main.java.model.account.LoginAccount;
import main.java.model.item.Item;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.Meeting;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.Message;
import main.java.model.message.MessageStorage;
import main.java.model.status.Status;
import main.java.model.status.StatusStorage;
import main.java.model.trade.Trade;
import main.java.model.trade.TradeStorage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class StorageFactory {

    final List<String> STORAGES = new ArrayList<>(Arrays.asList("ACCOUNT", "ITEM", "MESSAGE", "STATUS", "TRADE",
            "MEETING"));
    final String path;
    Map<String, String> filenameMap;
    Map<String, Object> dataMap;
    Map<String, Storage> storageMap;

    /**
     * Class Constructor.
     * Reading a config file: https://stackoverflow.com/questions/16273174/how-to-read-a-configuration-file-in-java
     *
     * @param resourcePath path of the resources folder
     * @param filenameConfig filenames corresponding to each Storage
     * @throws IOException occurs during config read/load
     */
    public StorageFactory(String resourcePath, String filenameConfig) throws IOException, InvalidStorageTypeException,
            ClassNotFoundException {
        path = resourcePath;
        Properties storages = new Properties();
        storages.load(new FileInputStream(path + filenameConfig));
        for (String storage: STORAGES) {
            filenameMap.put(storage, storages.getProperty("filenames." + storage));
            createStorage(storage);
        }
    }

    private void createStorage(String type) throws IOException, InvalidStorageTypeException, ClassNotFoundException {
        String filePath = path + filenameMap.get(type);
        File file = new File(filePath);
        FileReadWriter fileReadWriter = new FileReadWriter(filePath);
        if (file.exists()) {
            try {
                switch (type) {
                    case "ACCOUNT":
                        Map<String, LoginAccount> accounts = (Map<String, LoginAccount>) fileReadWriter.readFromFile();
                        dataMap.put(type, accounts);
                        storageMap.put(type, new AccountStorage(accounts));
                    case "ITEM":
                        Map<Integer, Item> items = (Map<Integer, Item>) fileReadWriter.readFromFile();
                        dataMap.put(type, items);
                        storageMap.put(type, new ItemStorage(items));
                    case "MESSAGE":
                        List<Message> messages = (List<Message>) fileReadWriter.readFromFile();
                        dataMap.put(type, messages);
                        storageMap.put(type, new MessageStorage(messages));
                    case "MEETING":
                        List<Meeting> meetings = (List<Meeting>) fileReadWriter.readFromFile();
                        dataMap.put(type, meetings);
                        storageMap.put(type, new MeetingStorage(meetings));
                    case "STATUS":
                        Map<String, List<Status>> statuses = (Map<String, List<Status>>) fileReadWriter.readFromFile();
                        dataMap.put(type, statuses);
                        storageMap.put(type, new StatusStorage(statuses));
                    case "TRADE":
                        List<Trade> trades = (List<Trade>) fileReadWriter.readFromFile();
                        dataMap.put(type, trades);
                        storageMap.put(type, new TradeStorage(trades));
                    default:
                        throw new InvalidStorageTypeException();
                }
            } catch(EOFException ignored) {}
        } else {
            assert file.createNewFile();
        }
    }

    /**
     * Get a Storage by type.
     *
     * @param type Storage type
     * @return Storage
     * @throws InvalidStorageTypeException if improper Storage type is inputted
     */
    public Storage getStorage(String type) throws InvalidStorageTypeException {
        if (!STORAGES.contains(type)) { throw new InvalidStorageTypeException(); }
        return storageMap.get(type);
    }

    /**
     * Save Storage data to file by type.
     *
     * @param type Storage type
     * @throws IOException if an error occurs during writing to file
     * @throws InvalidStorageTypeException if improper Storage type is inputted
     */
    public void saveStorage(String type) throws IOException, InvalidStorageTypeException {
        if (!STORAGES.contains(type)) { throw new InvalidStorageTypeException(); }
        new FileReadWriter(path + filenameMap.get(type)).saveToFile(dataMap.get(type));
    }
}
