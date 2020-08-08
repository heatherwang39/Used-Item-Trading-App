package main.java.presenter;

import java.util.*;

/**
 * Presenter for formatting Message data into an easy to output GUI format
 * Useful for Message tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class MessagePresenter {

    /**
     * Initializes a new MessagePresenter
     */
    public MessagePresenter() {}

    /**
     * Formats a list of hashmaps representing data of messages for GUI Lists to display
     * Each message will be formatted into a list with two elements, the first is the message header, and the second is
     * the content of the message to appear when clicked
     * Format of message header: "Title from Sender (date)"
     * Format of message content: "content"
     * Example header: "Hi, interested to trade? from Fadi (2020-08-08 3:51:55)"
     * Example content: "I have some cool items in my inventory"
     *
     * @param messages List of hashmaps that contain message data
     * @return List of formatted lists of Strings of each message data
     */
    public List<List<String>> formatMessageToListView(List<HashMap<String, String>> messages) {
        List<List<String>> formatMessage = new ArrayList<>();
        for (HashMap<String, String> messageData : messages) {
            String messageInfo = messageData.get("title") + " from " +
                    messageData.get("sender") +
                    " (" +
                    messageData.get("date") +
                    ")";
            formatMessage.add(Arrays.asList(messageInfo, messageData.get("content")));
        }
        return formatMessage;
    }
}
