package main.java.MessagePackage;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A Message class sent from a user to another user.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class UserMessage extends Message {

    private String title;
    private String content;
    private String sender;
    private List<String> recipients;
    private Date date;

    /**
     * Class constructor
     *
     * @param title      the title of the message
     * @param content    the content of the message
     * @param sender     username of message sender
     * @param recipients username of message receiver
     */
    public UserMessage(String title, String content, String sender, List<String> recipients) {
        super(title, content, recipients);
        this.sender = sender;
    }

    /**
     * Returns a HashMap of all displayable and obtainable information from the Entity
     * @return The map's key is the attribute name and the value is the attribute data
     */
    @Override
    public List<List<String>> getData() {
        List<List<String>> lst = super.getData();
        lst.add(Arrays.asList("Sender", sender));
        return lst;
    }
}
