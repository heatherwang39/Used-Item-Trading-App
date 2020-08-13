package main.java.system;

import main.java.model.account.AccountStorage;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.trade.TradeStorage;

import java.io.IOException;

/**
 * A class that stores all of the Storage Classes and the Storage Gateway
 *
 * In essence, this is the backend of the program.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class StorageDepot {
    private final StorageGateway sg;
    private final StorageFactory storageFactory = new StorageFactory();
    private final AccountStorage accountStorage;
    private final ItemStorage itemStorage;
    private final MessageStorage messageStorage;
    private final MeetingStorage meetingStorage;
    private final TradeStorage tradeStorage;


    /** Initialize an instance of Storage Depot
     *
     * @param sg The Storage Gateway
     * @throws IOException if error during reading file
     * @throws ClassNotFoundException if serialized Class not found
     */
    protected StorageDepot(StorageGateway sg) throws IOException, ClassNotFoundException {
        this.sg = sg;

        accountStorage = (AccountStorage) storageFactory.getStorage(sg, StorageEnum.ACCOUNT);
        itemStorage = (ItemStorage) storageFactory.getStorage(sg, StorageEnum.ITEM);
        messageStorage = (MessageStorage) storageFactory.getStorage(sg, StorageEnum.MESSAGE);
        meetingStorage = (MeetingStorage) storageFactory.getStorage(sg, StorageEnum.MEETING);
        tradeStorage = (TradeStorage) storageFactory.getStorage(sg, StorageEnum.TRADE);

        meetingStorage.attachMeetingObserver(tradeStorage);

        tradeStorage.attachTradeObserver(itemStorage);
        tradeStorage.attachTradeObserver(accountStorage);
    }


    /** Return the AccountStorage involved with the program
     *
     * @return the AccountStorage
     */
    public AccountStorage getAccountStorage(){
        return accountStorage;
    }


    /** Return the ItemStorage involved with the program
     *
     * @return the ItemStorage
     */
    public ItemStorage getItemStorage(){
        return itemStorage;
    }


    /** Return the MessageStorage involved with the program
     *
     * @return the MessageStorage
     */
    public MessageStorage getMessageStorage(){
        return messageStorage;
    }


    /** Return the MeetingStorage involved with the program
     *
     * @return the MeetingStorage
     */
    public MeetingStorage getMeetingStorage(){
        return meetingStorage;
    }


    /** Return the TradeStorage involved with the program
     *
     * @return the TradeStorage
     */
    public TradeStorage getTradeStorage(){
        return tradeStorage;
    }


    /** Return the StorageGateway involved with the program
     *
     * @return the StorageGateway
     */
    public StorageGateway getStorageGateway(){return sg;}
}