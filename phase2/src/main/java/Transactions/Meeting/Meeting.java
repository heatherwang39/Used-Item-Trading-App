package main.java.Transactions.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A concrete class in the Trade system representing a meeting in the program
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class Meeting {
    private int meetingID;
    private String place;
    private LocalDateTime time;
    private List<String> attendees;
    private List<Boolean> accepted;
    private List<Boolean> confirmed;


    //Initializer

    /**
     * Class Constructor.
     *
     * @param meetingID The Unique ID of the meeting
     * @param attendees The Attendees of the meeting
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     */
    public Meeting(int meetingID, List<String> attendees, String place, LocalDateTime time){
        this.meetingID = meetingID;
        this.place = place;
        this.time = time;

        this.attendees = attendees;
        accepted = new ArrayList();
        confirmed = new ArrayList();
        int i = 0;
        while(i < attendees.size()) {
            accepted.add(false);
            confirmed.add(false);
        }
    }


    //Getters


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
    public LocalDateTime getTime(){
        return time;
    }


    /** Return a list containing all the attendees of this meeting
     *
     * @return A list containing all the attendees of this meeting
     */
    public List<String> getAttendees(){
        return new ArrayList(attendees);
    }


    /** Return whether or not the meeting has been accepted (by everyone)
     *
     * @return whether the meeting has been accepted
     */
    public boolean isAccepted(){
        for(Boolean a: accepted){
            if(!a){
                return false;
            }
        }
        return true;
    }


    /** Return whether or not the meeting has been confirmed (by everyone)
     *
     * @return whether the meeting has been confirmed
     */
    public boolean isConfirmed(){
        for(Boolean a: accepted){
            if(!a){
                return false;
            }
        }
        return true;
    }


    /** Return a list containing all the attendees that haven't accepted this meeting
     *
     * @return A list containing all the attendees that haven't accepted this meeting
     */
    public List<String> getUnacceptedAttendees(){
        List Unaccepted = new ArrayList();
        int i = 0;
        while(i < attendees.size()){
            if(!accepted.get(i)){
                Unaccepted.add(attendees.get(i));
            }
        }
        return Unaccepted;
    }


    /** Return a list containing all the attendees that haven't confirmed this meeting
     *
     * @return A list containing all the attendees that haven't confirmed this meeting
     */
    public List<String> getUnconfirmedAttendees(){
        List unconfirmed = new ArrayList();
        int i = 0;
        while(i < attendees.size()){
            if(!accepted.get(i)){
                unconfirmed.add(attendees.get(i));
            }
        }
        return unconfirmed;
    }


    //Pseudo-Setters




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
}
