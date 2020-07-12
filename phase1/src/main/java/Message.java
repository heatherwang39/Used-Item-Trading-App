package main.java;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

abstract class Message implements Entity {
    private String title;
    private String content;
    private String sender;
    private String to;
    private Date generatedDate;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * Class constructor
     * @param title the title of the message
     * @param content the content of the message
     * @param from username of message sender
     * @param to username of message receiver
     */
    public Message(String title, String content, String from, String to) {
        this.title = title;
        this.content = content;
        this.from = from;
        this.to = to;
        this.generatedDate = new Date();
    }


}