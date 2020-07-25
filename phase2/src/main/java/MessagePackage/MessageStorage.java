package main.java.MessagePackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Use case class for storing messages, sending messages to users and letting system send messages.
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */

public class MessageStorage {

    /**
     * A Hashmap of username to the list of sent messages and received messages.
     */
    private Map<String, List<List<Message>>> messages;


    /**
     * Creates an MessageStorage with lists of messages list that are empty
     */
    public MessageStorage(Map<String, List<List<Message>>> messages){
        this.messages = messages;
    }

    /**
     * Initializes a new Message based on the given type of message
     * @param messageType the type of the message, systemMessage or userMessage
     * @param title the title of the message
     * @param content the content of the message
     * @param sender the username of the user who send the message
     * @param recipients a list of usernames of the users that receive the message
     * @return The created message
     * @throws NoSuchMessageTypeException when incorrect message type typed in
     */
    public Message createNewMessage(MessageType messageType, String title, String content, String sender, List<String> recipients)
            throws NoSuchMessageTypeException {
        switch (messageType){
            case USER:
                return new UserMessage(title, content, sender, recipients);
            case SYSTEM:
                return new SystemMessage(content, recipients);
            default:
                throw new NoSuchMessageTypeException();
        }
    }

    private List<List<Message>> getMessageByUser(String username){
        List<List<Message>> userMessages = messages.get(username);

        return userMessages;
    }

    private List<Message> getSentMessageByUser(String username) {
        List<List<Message>> userMessages = getMessageByUser(username);
        List<Message> sentMessages = userMessages.get(0) ;
        return sentMessages;
    }

    private List<Message> getReceivedMessageByUser(String username) {
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
     */
    public void sendUserMessage(String title, String content, String sender, List<String> recipients){
        UserMessage ums = new UserMessage(title, content, sender, recipients);
        if(messages.containsKey(sender)){getSentMessageByUser(sender).add(ums);}
        else{
            List<List<Message>> newSentBox = new ArrayList<>();
            newSentBox.get(0).add(ums);
            messages.put(sender,newSentBox);
        }
        for (String r : recipients) {
            if(messages.containsKey(recipients)){getReceivedMessageByUser(r).add(ums);}
            else{
                List<List<Message>> newInBox = new ArrayList<>();
                newInBox.get(1).add(ums);
                messages.put(r,newInBox);
            }
        }
    }

    /**
     * Allow system to send message to users
     * @param content the content of the message
     * @param recipients a list of usernames of the users that receive the message
     */
    public void sendSystemMessage(String content, List<String> recipients){
        SystemMessage sms = new SystemMessage(content, recipients);
        for (String r : recipients) {
            if(messages.containsKey(recipients)){getReceivedMessageByUser(r).add(sms);}
            else{
                List<List<Message>> newInBox = new ArrayList<>();
                newInBox.get(1).add(sms);
                messages.put(r,newInBox);
            }
        }
    }

    /**
     * Allow user to check the received message
     * @param username the username of the user
     */
    public List<List<List<String>>> checkInBox(String username){
        List<List<List<String>>> info = new ArrayList<>();
        List<Message> inBox = getSentMessageByUser(username);
        for(Message ms: inBox){
            List<List<String>> msData = ms.getData();
            info.add(msData);
        }
        return info;
    }

    /**
     * Allow user to check the sent message
     * @param username the username of the user
     */
    public List<List<List<String>>> checkSentBox(String username){
        List<List<List<String>>> info = new ArrayList<>();
        List<Message> sentBox = getSentMessageByUser(username);
        for(Message ms: sentBox){
            List<List<String>> msData = ms.getData();
            info.add(msData);
        }
        return info;
    }

}