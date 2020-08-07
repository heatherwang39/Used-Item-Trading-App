package main.java.controller.deprectaed;

import main.java.controller.MutedAccountException;
import main.java.controller.RecipientNotFoundException;
import main.java.controller.deprectaed.AccountController;
import main.java.controller.deprectaed.GuestController;
import main.java.model.account.AccountStorage;
import main.java.model.message.EmptyContentException;
import main.java.model.message.EmptyRecipientListException;
import main.java.model.message.EmptyTitleException;
import main.java.model.message.MessageStorage;
import main.java.model.status.StatusStorage;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A Controller for Accounts that can log in
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public abstract class LoginAccountController extends GuestController implements AccountController {
    protected String username;
    protected final MessageStorage messageStorage;
    protected final AccountStorage accountStorage;
    protected final StatusStorage statusStorage;
    //private List<String> accountStatus;

    /** Class Constructor
     *
     * @param username Username of the Account
     * @param storageGateway Gateway class for reading and writing Storage Data
     */
    public LoginAccountController(String username, StorageGateway storageGateway) throws IOException, ClassNotFoundException {
        super(storageGateway);
        this.username = username;
        StorageFactory storageFactory = new StorageFactory();
        this.messageStorage = (MessageStorage)storageFactory.getStorage(storageGateway, StorageEnum.MESSAGE);
        this.accountStorage = (AccountStorage)storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
        this.statusStorage = (StatusStorage) storageFactory.getStorage(storageGateway,StorageEnum.STATUS);
        //this.accountStatus = statusStorage.getAccountStatusStrings(username);
    }

    /** Retrieve the Inbox Data associated with this Account
     *
     * @return Inbox Data associated with this Account
     */
    public List<HashMap<String, String>> getInboxData(){
        return messageStorage.getInboxData(username);
    }

    /** Retrieve the Outbox Data associated with this Account
     *
     * @return Outbox Data associated with this Account
     */
    public List<HashMap<String, String>> getOutboxData(){
        return messageStorage.getOutboxData(username);
    }

    /** Send a user message to the given user
     *
     * @param title The title of the message
     * @param content The content of the message
     * @param recipients The recipient of the message
     * @throws RecipientNotFoundException Thrown if a given recipient does not exist
     * @throws EmptyTitleException the title is empty
     * @throws EmptyContentException the content is empty
     * @throws EmptyRecipientListException the recipient list is empty
     * @throws MutedAccountException the user's status is muted and cannot send messages
     */

    public void sendUserMessage(String title, String content, List<String> recipients)
            throws RecipientNotFoundException, EmptyTitleException, EmptyRecipientListException, EmptyContentException, MutedAccountException, IOException {
        if(statusStorage.containsStatus(username,"MUTED")){
            throw new MutedAccountException();
        }

        for(String user : recipients){
            if(!accountStorage.isUsernameInUse(user)){
                throw new RecipientNotFoundException();
            }
        }
        messageStorage.sendUserMessage(title, content, username, recipients);
        storageGateway.saveStorageData(StorageEnum.MESSAGE);
    }
}
