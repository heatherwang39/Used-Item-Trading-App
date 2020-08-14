package main.java.system;

import main.java.model.Storage;
import main.java.model.account.AccountStorage;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.trade.TradeStorage;

import java.io.IOException;

/**
 * A Factory class for returning Storages
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class StorageFactory {

    /**
     * Get a Storage by type.
     *
     * @param type Storage type
     * @return Storage
     * @throws IOException if error during reading file
     * @throws ClassNotFoundException if serialized Class not found
     */
    public Storage getStorage(StorageGateway sg, StorageEnum type) throws IOException, ClassNotFoundException {
        Storage storage;
        switch (type) {
            case ACCOUNT:
                storage = new AccountStorage();
                break;
            case ITEM:
                storage = new ItemStorage();
                break;
            case MESSAGE:
                storage = new MessageStorage();
                break;
            case MEETING:
                storage = new MeetingStorage();
                break;
            case TRADE:
                storage = new TradeStorage();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        storage.setStorageData(sg.getStorageData(type, storage.getNewStorageData()));
        sg.saveStorageData(type);
        return storage;
    }
}
