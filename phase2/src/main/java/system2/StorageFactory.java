package main.java.system2;

import main.java.model.Storage;
import main.java.model.account.AccountStorage;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.status.StatusStorage;
import main.java.model.trade.TradeStorage;

import java.io.IOException;

/**
 *
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
        Object data = sg.getStorageData(type);
        switch (type) {
            case ACCOUNT:
                return new AccountStorage(data);
            case ITEM:
                return new ItemStorage(data);
            case MESSAGE:
                return new MessageStorage(data);
            case MEETING:
                return new MeetingStorage(data);
            case STATUS:
                return new StatusStorage(data);
            case TRADE:
                return new TradeStorage(data);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
