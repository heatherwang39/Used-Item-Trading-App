package main.java.model.meeting;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A concrete class in the Trade system representing a meeting in the program
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class Meeting implements Serializable {
    private final int meetingID;
    private final String place;
    private final LocalDateTime dateTime;
    private final List<String> attendees;
    private final List<Boolean> accepted;
    private final List<Boolean> confirmed;

    private Boolean cancelled;

    /**
     * Class Constructor.
     *
     * @param meetingID The Unique ID of the meeting
     * @param attendees The Attendees of the meeting
     * @param place The place where the meeting will take place
     * @param dateTime The time where the meeting will take place
     */
    public Meeting(int meetingID, List<String> attendees, String place, LocalDateTime dateTime){
        this.meetingID = meetingID;
        this.place = place;
        this.dateTime = dateTime;
        this.attendees = attendees;
        int n = attendees.size();
        // From: https://stackoverflow.com/questions/5600668/how-can-i-initialize-an-arraylist-with-all-zeroes-in-java
        accepted = new ArrayList<>(Collections.nCopies(n, false));
        confirmed = new ArrayList<>(Collections.nCopies(n, false));

        cancelled = false;
    }


    // Getters

    /** Return the unique ID of the meeting
     *
     * @return The meeting ID
     */
    public int getMeetingID() {
        return meetingID;
    }

    /** Return the place at which the meeting will take place
     *
     * @return The place where the meeting will occur
     */
    public String getPlace(){
        return place;
    }

    /** Return the time when the meeting will take place
     *
     * @return The time when the meeting will take place
     */
    public LocalDateTime getDateTime(){
        return dateTime;
    }

    /**
     * Get the Message's date of creation in string format.
     * From: https://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public String getStringDateTime() { return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime); }

    /** Return a list containing all the attendees of this meeting
     *
     * @return A list containing all the attendees of this meeting
     */
    public List<String> getAttendees(){
        return new ArrayList<>(attendees);
    }

    /** Return whether or not the meeting has been accepted (by everyone)
     *
     * @return whether the meeting has been accepted
     */
    public boolean isAccepted(){
        for (boolean a: accepted) if(!a) return false;
        return true;
    }

    /** Return whether or not the meeting has been confirmed (by everyone)
     *
     * @return whether the meeting has been confirmed
     */
    public boolean isConfirmed(){
        for (boolean a: confirmed) if(!a) return false;
        return true;
    }

    /** Return a list containing all the attendees that haven't accepted this meeting
     *
     * @return A list containing all the attendees that haven't accepted this meeting
     */
    public List<String> getUnacceptedAttendees(){
        List<String> unaccepted = new ArrayList<>();
        for (int i = 0; i < attendees.size(); i++)
            if(!accepted.get(i)) unaccepted.add(attendees.get(i));
        return unaccepted;
    }

    /** Return a list containing all the attendees that haven't confirmed this meeting
     *
     * @return A list containing all the attendees that haven't confirmed this meeting
     */
    public List<String> getUnconfirmedAttendees(){
        List<String> unconfirmed = new ArrayList<>();
        for (int i = 0; i < attendees.size(); i++)
            if(!confirmed.get(i)) unconfirmed.add(attendees.get(i));
        return unconfirmed;
    }


    // Pseudo-setters

    /** Record the fact that the attendee has accepted the meeting. Return true if this change has been successfully
     * accepted.
     *
     * Pre-condition: The attendee is an actual attendee in the meeting
     *
     * @param attendee The attendee accepting the meeting
     * @return Whether this change has been recorded
     */
    public boolean acceptMeeting(String attendee){
        int i = attendees.indexOf(attendee);
        if(!accepted.get(i)){
            accepted.set(i, true);
            return true;
        }
        return false;
    }

    /** Record the fact that the attendee has confirmed the meeting. Return true if this change has been successfully
     * accepted.
     *
     * Pre-conditions:
     * 1) The attendee is an actual attendee in the meeting
     * 2) The meeting has already taken place (i.e., in terms of time)
     *
     * @param attendee The attendee confirming the meeting
     * @return Whether this change has been recorded
     */
    public boolean confirmMeeting(String attendee){
        int i = attendees.indexOf(attendee);
        if(!confirmed.get(i)){
            confirmed.set(i, true);
            return true;
        }
        return false;
    }

    /** Return whether or not the Meeting is cancelled
     *
     * @return Whether the Meeting is cancelled
     */
    public boolean isNotCancelled(){
        return !cancelled;
    }

    /** Cancel the Meeting
     *
     * @return Whether the Meeting was successfully cancelled
     */
    public boolean cancel(){
        if(cancelled){
            return false;
        }
        cancelled = true;
        return true;
    }
}
