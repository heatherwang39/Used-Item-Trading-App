package main.java;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A Message class sent to one or more recipients at a date.
 * @author Heather Wang, Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class Message implements Entity {

    private String title;
    private String content;
    private List<String> recipients;
    private Date date;

    /**
     * Class constructor
     * @param title the title of the message
     * @param content the content of the message
     * @param recipients username of message receiver
     */
    public Message(String title, String content, List<String> recipients) {
        this.title = title;
        this.content = content;
        this.recipients = recipients;
        this.date = new Date();
    }

    /**
     * Returns a HashMap of all displayable and obtainable information from the Entity
     * @return The map's key is the attribute name and the value is the attribute data
     */
    @Override
    public List<List<String>> getData() {
        List<List<String>> lst = new ArrayList<>();
        lst.add(Arrays.asList("Title", title));
        // From: https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lst.add(Arrays.asList("Date", formatter.format(date)));
        lst.add(Arrays.asList("Content", content));
        StringBuilder recipients = new StringBuilder();
        for (String r: this.recipients) {
            recipients.append(r);
        }
        lst.add(Arrays.asList("Recipients", recipients.toString()));
        return lst;
    }
}