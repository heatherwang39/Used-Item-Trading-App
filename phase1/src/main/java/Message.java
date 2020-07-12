package main.java;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

abstract class Message implements Entity {
    private String title;
    private String content;
    private String sender;
    private String recipient;
    private Date generatedDate;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * Class constructor
     * @param title the title of the message
     * @param content the content of the message
     * @param sender username of message sender
     * @param recipient username of message receiver
     */
    public Message(String title, String content, String sender, String recipient) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.generatedDate = new Date();
    }


}