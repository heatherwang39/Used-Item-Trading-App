package main.java.controller;

import main.java.model.account.AccountStorage;
import main.java.model.message.*;
import main.java.system.StorageDepot;
import main.java.system.StorageGateway;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
     * @param storageDepot storageDepot associated with the program
     */
    public MessageController(StorageDepot storageDepot, String username) {
        storageGateway = storageDepot.getStorageGateway();
        messageStorage = storageDepot.getMessageStorage();
        accountStorage = storageDepot.getAccountStorage();
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
        storageGateway.saveStorageData();
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
        storageGateway.saveStorageData();
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

    /**
     * Get a string of all recipients
     *
     * @param recipients message recipients
     * @return recipients list string
     */
    public String getRecipientString(List<String> recipients){
        return String.join(", ", recipients);
    }

    /**
     * Check if message is nonempty and if user exists
     *
     * @param messageTitle message title
     * @param messageContent message content
     * @param recipientList recipient list
     *
     * @return if message is valid
     */
    public boolean validMessage(String messageTitle, String messageContent, List<String> recipientList) {
        if (!messageTitle.isEmpty() && !messageContent.isEmpty()) {
            for (String s: recipientList) {
                if (!accountStorage.isUsernameInUse(s)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Check if message is nonempty
     *
     * @param messageTitle message title
     * @param messageContent message content
     *
     * @return if message is valid
     */
    public boolean validMessage(String messageTitle, String messageContent) {
        return !messageTitle.isEmpty() && !messageContent.isEmpty();
    }
}
