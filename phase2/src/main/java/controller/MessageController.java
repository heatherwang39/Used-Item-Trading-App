package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.message.*;
import main.java.system2.StorageEnum;
import main.java.system2.StorageFactory;
import main.java.system2.StorageGateway;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * A Controller for sending Messages, checking InBox and OutBox
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */

public class MessageController {
    private final StorageGateway storageGateway;
    private final MessageStorage messageStorage;
    private final AccountStorage accountStorage;
    private final String username;

    /**
     * Class constructor for MessageController
     *
     * @param storageGateway gateway for loading and saving information
     */
    public MessageController(StorageGateway storageGateway, String username) throws IOException, ClassNotFoundException {
        this.storageGateway = storageGateway;
        StorageFactory storageFactory = new StorageFactory();
        messageStorage = (MessageStorage) storageFactory.getStorage(storageGateway, StorageEnum.MESSAGE);
        accountStorage = (AccountStorage) storageFactory.getStorage(storageGateway, StorageEnum.ACCOUNT);
        this.username = username;
    }

    /**
     * Creates a new UserMessage.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @param recipients a list of usernames of the users that receive the message
     * @throws EmptyTitleException the title is empty
     * @throws EmptyContentException the content is empty
     * @throws EmptyRecipientListException the recipient list is empty
     */
    public void sendUserMessage(String title, String content, List<String> recipients) throws EmptyTitleException,
            EmptyContentException, EmptyRecipientListException, IOException {
        messageStorage.sendUserMessage(title, content, username, recipients);
        storageGateway.saveStorageData(StorageEnum.MESSAGE);
        String recipientString = getRecipientString(recipients);
        showMessageDialog(null, "Message sent to: " + recipientString);
    }


    /**
     * Creates a new SystemMessage and send to all admins.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @throws EmptyTitleException the title is empty
     * @throws EmptyContentException the content is empty
     * @throws EmptyRecipientListException the recipient list is empty
     */
    public void sendRequestToSystem(String title, String content) throws EmptyTitleException, EmptyContentException,
            EmptyRecipientListException, IOException {
        List<String> recipients = accountStorage.getAdmins();
        messageStorage.sendSystemMessage(title, content, recipients);
        storageGateway.saveStorageData(StorageEnum.MESSAGE);
        showMessageDialog(null, "Message sent to admins");
    }

    /**
     * Allow the current user to check all received Messages.
     *
     * @return data of Messages in inbox
     */
    public List<HashMap<String, String>> getInbox() {
        return messageStorage.getInboxData(username);
    }

    /**
     * Allow the current user to check all received Messages.
     *
     * @return data of Messages in inbox
     */
    public List<HashMap<String, String>> getOutbox() {
        return messageStorage.getOutboxData(username);
    }

    private String getRecipientString(List<String> recipients){
        String s = null;
        for (String recipient: recipients){
            s += recipient + ", ";
        }
        return s;
    }

}
