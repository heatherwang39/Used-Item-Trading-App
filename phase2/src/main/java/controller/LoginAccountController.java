package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.item.ItemStorage;
import main.java.model.message.MessageStorage;

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
    private MessageStorage messageStorage;
    private AccountStorage accountStorage;

    /** Class Constructor
     *
     * @param username Username of the Account
     * @param messageStorage A MessageStorage object associated with this system
     * @param itemStorage An itemStorage object associated with this system
     */
    public LoginAccountController(String username, MessageStorage messageStorage, ItemStorage itemStorage,
                                  AccountStorage accountStorage){
        super(itemStorage);
        this.username = username;
        this.messageStorage = messageStorage;
        this.accountStorage = accountStorage;
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
     */
    public void sendUserMessage(String title, String content, List<String> recipients)
            throws RecipientNotFoundException{
        for(String user : recipients){
            if(!accountStorage.isUsernameInUse(user)){
                throw new RecipientNotFoundException();
            }
        }
        messageStorage.sendUserMessage(title, content, username, recipients);
    }
}
