package main.java;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * An automated Message class sent from the system to a user.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class SystemMessage extends Message {

    private String title;
    private String content;
    private List<String> recipients;
    private Date date;

    /**
     * Class constructor
     *
     * @param content    the content of the message
     * @param recipients username of message receiver
     */
    public SystemMessage(String content, List<String> recipients) {
        super("SYSTEM AUTOMATED MESSAGE", content, recipients);
    }
}
