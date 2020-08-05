package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.item.ItemStorage;
import main.java.model.meeting.MeetingStorage;
import main.java.model.message.MessageStorage;
import main.java.model.status.StatusStorage;
import main.java.model.trade.*;
import main.java.model.account.AccountNotFoundException;
import main.java.system2.InvalidStorageTypeException;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;

/**
 * A Factory for Account Controllers
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public class AccountControllerFactory {
    private StorageFactory storageFactory;

    private AccountStorage accountStorage;
    private ItemStorage itemStorage;
    private MessageStorage messageStorage;
    private MeetingStorage meetingStorage;
    private StatusStorage statusStorage;
    private TradeStorage tradeStorage;


    public AccountControllerFactory(StorageGateway storageGateway, StorageFactory storageFactory){
        this.storageFactory = storageFactory;
        try{
            accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
            itemStorage = (ItemStorage) storageFactory.getStorage(storageGateway, StorageEnum.ITEM);
            messageStorage = (MessageStorage) storageFactory.getStorage(storageGateway, StorageEnum.MESSAGE);
            meetingStorage = (MeetingStorage) storageFactory.getStorage(storageGateway, StorageEnum.MEETING);
            statusStorage = (StatusStorage) storageFactory.getStorage(storageGateway, StorageEnum.STATUS);
            tradeStorage = (TradeStorage) storageFactory.getStorage(storageGateway, StorageEnum.TRADE);

            meetingStorage.attachMeetingObserver(tradeStorage);
            tradeStorage.attachTradeObserver(itemStorage);
        }
        catch(IOException | ClassNotFoundException ignored){}
    }



    /** Return the UserController associated with the given username. If no Account has the given username,
     * return a GuestController
     *
     * @param username The username of the user that's getting the Controller
     * @return The Controller associated with the given User
     * @throws NoAccountTypeException Thrown if no controller is associated with the User's Account Type
     */
    public AccountController getUserController(String username) throws NoAccountTypeException{
        try{String type = accountStorage.getType(username);
            if(type.equals("ADMIN")){return new AdminController();}
            if(type.equals("USER")){return new UserController();}
            else{throw new NoAccountTypeException();}
        }
        catch(AccountNotFoundException e){}
        return new GuestController(itemStorage);
    }


}
