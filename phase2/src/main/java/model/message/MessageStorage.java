package main.java.model.message;

import main.java.model.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Use case class for storing Messages and creating Messages.
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class MessageStorage implements Storage {

    private final List<Message> messages;

    /**
     * Class constructor.
     *
     * @param messages list of messages
     */
    public MessageStorage(Object messages) {
        this.messages = (List<Message>) messages;
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

    /**
     * Creates a HashMap containing the Message's data
     * @return Data in the form of {Label: Information, ...}
     */
    public HashMap<String, String> getData(Message m) {
        HashMap<String, String> data = new HashMap<>();
        data.put("title", m.getTitle());
        data.put("content", m.getContent());
        data.put("date", m.getStringDateTime());
        data.put("sender", m.getSender());
        data.put("recipients", String.join(",", m.getRecipients()));
        return data;
    }

    /**
     * Allow Account to check all received Messages.
     *
     * @param username Account username
     * @return data of Messages in inbox
     */
    public List<HashMap<String, String>> getInboxData(String username) {
        List<HashMap<String, String>> inbox = new ArrayList<>();
        for (Message m: getReceivedMessages(username)) { inbox.add(getData(m)); }
        return inbox;
    }

    /**
     * Allow Account to check all sent Messages.
     *
     * @param username Account username
     * @return data of Messages in outbox
     */
    public List<HashMap<String, String>> getOutboxData(String username) {
        List<HashMap<String, String>> inbox = new ArrayList<>();
        for (Message m: getSentMessages(username)) { inbox.add(getData(m)); }
        return inbox;
    }

}