package main.java;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageStorage {

    /**
     * A Hashmap of username to the list of sent messages and received messages.
     */
    private HashMap<String,List<List<Message>>> messages;
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
                messages = (HashMap<String,List<List<Message>>>) frw.readFromFile(path);
            } catch (EOFException e) {
                System.out.println("Cannot read from file");
            }
        } else {
            file.createNewFile();
        }
    }

    public List<List<Message>> getMessageByUser(String username) throws MessageNotFoundException {
        List<List<Message>> userMessages = messages.get(username);
        if (userMessages == null) {
            throw new MessageNotFoundException();
        }
        return userMessages;
    }

    public void sendUserMessage(String title, String content, String sender, List<String> recipients) throws MessageNotFoundException, IOException {
        UserMessage ms = new UserMessage(title, content, sender, recipients);
        List<List<Message>> senderMessages = getMessageByUser(sender);
        senderMessages.get(0).add(ms);
        List<List<Message>> recipientMessages;
        for (String r: recipients) {
            recipientMessages = getMessageByUser(r);
            recipientMessages.get(1).add(ms);
        }
        frw.saveToFile(messages,path);
    }


}
