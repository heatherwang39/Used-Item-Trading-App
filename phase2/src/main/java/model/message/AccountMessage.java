package main.java.model.message;

import java.util.List;

/**
 * AccountMessage represents a Message sent from a UserAccount to another UserAccount
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class AccountMessage extends Message {

    /**
     * Class constructor
     *
     * @param title      the title of the message
     * @param content    the content of the message
     * @param sender     username of message sender
     * @param recipients username of message receiver
     */
    public AccountMessage(String title, String content, String sender, List<String> recipients) {
        super(title, content, sender, recipients);
    }
}
