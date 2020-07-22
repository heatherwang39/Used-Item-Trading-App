package main.java;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Use case class for storing messages, sending messages to users and letting system send messages.
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class MessageStorage {

    /**
     * A Hashmap of username to the list of sent messages and received messages.
     */
    private HashMap<String, List<List<Message>>> messages;
    private FileReadWriter frw;
    private String path;

    /**
     * Creates an MessageStorage with lists of messages list that are empty
     *
     * @param filePath the path of the data file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public MessageStorage(String filePath) throws IOException, ClassNotFoundException {
        this.path = filePath;
        messages = new HashMap<>();
        File file = new File(path);
        frw = new FileReadWriter(path);
        if (file.exists()) {
            try {
                messages = (HashMap<String, List<List<Message>>>) frw.readFromFile(path);
            } catch (EOFException e) {
                System.out.println("Cannot read from file");
            }
        } else {
            file.createNewFile();
        }
    }

    private List<List<Message>> getMessageByUser(String username) throws MessageNotFoundException {
        List<List<Message>> userMessages = messages.get(username);
        if (userMessages == null) {
            throw new MessageNotFoundException();
        }
        return userMessages;
    }

    private List<Message> getSentMessageByUser(String username) throws MessageNotFoundException {
        List<List<Message>> userMessages = getMessageByUser(username);
        List<Message> sentMessages = userMessages.get(0) ;
        return sentMessages;
    }

    private List<Message> getReceivedMessageByUser(String username) throws MessageNotFoundException {
        List<List<Message>> userMessages = getMessageByUser(username);
        List<Message> receivedMessages = userMessages.get(1) ;
        return receivedMessages;
    }

    /**
     * Allow user to send message to other users
     * @param title the title of the message
     * @param content the content of the message
     * @param sender the username of the user who send the message
     * @param recipients a list of usernames of the users that receive the message
     * @throws MessageNotFoundException when the user have no stored message
     * @throws IOException error occurs when saving to file
     */
    public void sendUserMessage(String title, String content, String sender, List<String> recipients) throws MessageNotFoundException, IOException {
        UserMessage ums = new UserMessage(title, content, sender, recipients);
        getSentMessageByUser(sender).add(ums);
        for (String r : recipients) {
            getReceivedMessageByUser(r).add(ums);
        }
        frw.saveToFile(messages, path);
    }

    /**
     * Allow system to send message to users
     * @param content the content of the message
     * @param recipients a list of usernames of the users that receive the message
     * @throws MessageNotFoundException when the user have no stored message
     * @throws IOException error occurs when saving to file
     */
    public void sendSystemMessage(String content, List<String> recipients) throws MessageNotFoundException, IOException {
        SystemMessage sms = new SystemMessage(content, recipients);
        for (String r : recipients) {
            getReceivedMessageByUser(r).add(sms);
        }
        frw.saveToFile(messages, path);
    }
}