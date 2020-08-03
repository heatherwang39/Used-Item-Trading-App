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
    public StorageFactory(String resourcePath, String filenameConfig) throws IOException {
        path = resourcePath;
        Properties storages = new Properties();
        storages.load(new FileInputStream(path + filenameConfig));
        filenameMap = (Map) storages;
    }

    private Storage createStorage(String type) throws IOException, InvalidStorageTypeException, ClassNotFoundException {
        String filePath = path + filenameMap.get(type);
        File file = new File(filePath);
        FileReadWriter fileReadWriter = new FileReadWriter(filePath);
        assert file.exists() || file.createNewFile();
        Storage storage = null;
        try {
            switch (type) {
                case "ACCOUNT":
                    Map<String, LoginAccount> accounts = (Map<String, LoginAccount>) fileReadWriter.readFromFile();
                    dataMap.put(type, accounts);
                    storage = new AccountStorage(accounts);
                    storageMap.put(type, storage);
                case "ITEM":
                    Map<Integer, Item> items = (Map<Integer, Item>) fileReadWriter.readFromFile();
                    dataMap.put(type, items);
                    storage = new ItemStorage(items);
                    storageMap.put(type, storage);
                case "MESSAGE":
                    List<Message> messages = (List<Message>) fileReadWriter.readFromFile();
                    dataMap.put(type, messages);
                    storage = new MessageStorage(messages);
                    storageMap.put(type, storage);
                case "MEETING":
                    List<Meeting> meetings = (List<Meeting>) fileReadWriter.readFromFile();
                    dataMap.put(type, meetings);
                    storage = new MeetingStorage(meetings);
                    storageMap.put(type, storage);
                case "STATUS":
                    Map<String, List<Status>> statuses = (Map<String, List<Status>>) fileReadWriter.readFromFile();
                    dataMap.put(type, statuses);
                    storage = new StatusStorage(statuses);
                    storageMap.put(type, storage);
                case "TRADE":
                    List<Trade> trades = (List<Trade>) fileReadWriter.readFromFile();
                    dataMap.put(type, trades);
                    storage = new TradeStorage(trades);
                    storageMap.put(type, storage);
                default:
                    throw new InvalidStorageTypeException();
            }
        } catch (EOFException ignored) {}
        return storage;
    }

    /**
     * Get a Storage by type.
     *
     * @param type Storage type
     * @return Storage
     * @throws InvalidStorageTypeException if improper Storage type is inputted
     */
    public Storage getStorage(String type) throws InvalidStorageTypeException, IOException, ClassNotFoundException {
        if (!STORAGES.contains(type)) { throw new InvalidStorageTypeException(); }
        Storage storage = storageMap.get(type);
        if (storage == null) {
            storage = createStorage(type);
        }
        return storage;
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
