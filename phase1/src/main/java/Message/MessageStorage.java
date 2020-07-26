package main.java.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case class for storing Messages and creating Messages.
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class MessageStorage {

    private final List<Message> messages;

    /**
     * Class constructor.
     */
    public MessageStorage(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Receive a list of all Messages received by an Account.
     *
     * @param username Account username
     * @return Received Messages
     */
    private List<Message> getReceivedMessages(String username) {
        List<Message> messages = new ArrayList<>();
        for (Message m: this.messages) {
            if (m.getRecipients().contains(username)){
                messages.add(m);
            }
        }
        return messages;
    }

    /**
     * Receive a list of all Messages sent by an Account.
     *
     * @param username Account username
     * @return Sent Messages
     */
    private List<Message> getSentMessages(String username) {
        List<Message> messages = new ArrayList<>();
        for (Message m: this.messages) {
            if (m.getSender().equals(username)){
                messages.add(m);
            }
        }
        return messages;
    }

    /**
     * Creates a new UserMessage.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @param sender the username of the user who send the message
     * @param recipients a list of usernames of the users that receive the message
     */
    public void sendUserMessage(String title, String content, String sender, List<String> recipients) {
        messages.add(new AccountMessage(title, content, sender, recipients));
    }

    /**
     * Creates a new SystemMessage.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @param recipients a list of usernames of the users that receive the message
     */
    public void sendSystemMessage(String title, String content, List<String> recipients) {
        messages.add(new SystemMessage(title, content, recipients));
    }
}