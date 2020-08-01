package main.java.model.message;

import java.util.List;

/**
 * SystemMessage is an automated Message sent to an Account.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class SystemMessage extends Message {

    /**
     * Class constructor.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @param recipients username of message receiver
     */
    public SystemMessage(String title, String content, List<String> recipients) {
        super("SYSTEM AUTOMATED MESSAGE: " + title, content, null, recipients);
    }
}
