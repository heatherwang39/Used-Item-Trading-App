package main.java.model.message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Message represents a message sent to a user at a particular date.
 *
 * @author Heather Wang, Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public abstract class Message implements Serializable {

    private final String title;
    private final String content;
    private final String sender;
    private final List<String> recipients;
    private final LocalDateTime dateTime;

    /**
     * Class constructor.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @param recipients username of message receiver
     */
    public Message(String title, String content, String sender, List<String> recipients) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.recipients = recipients;
        this.dateTime = LocalDateTime.now();
    }

    /**
     * Get the Message's title.
     *
     * @return title
     */
    public String getTitle() { return title; }

    /**
     * Get the Message's content.
     *
     * @return content
     */
    public String getContent() { return content; }

    /**
     * Get the username of the Account that created the Message.
     * @return Account username
     */
    public String getSender() { return sender; }

    /**
     * Get a list of Account usernames that the message was sent to.
     *
     * @return Account usernames
     */
    public List<String> getRecipients() { return new ArrayList<>(recipients); }

    /**
     * Get the Message's date of creation.
     *
     * @return date
     */
    public LocalDateTime getDateTime() { return dateTime; }

    /**
     * Get the Message's date of creation in string format.
     * From: https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public String getStringDateTime() { return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); }
}