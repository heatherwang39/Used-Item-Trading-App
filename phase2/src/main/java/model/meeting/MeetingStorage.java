package main.java.model.meeting;

import main.java.model.Storage;
import main.java.model.trade.TradeObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Use case class for initializing, storing, recording changes and retrieving information regarding Meeting.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class MeetingStorage  implements Storage, MeetingObservee{
    private final List<Meeting> meetings;

    private final List<MeetingObserver> observers = new ArrayList<>();


    /** Class Constructor
     *
     * @param meetings The list of meetings stored by this particular instance of MeetingStorage
     */
    public MeetingStorage(Object meetings){ this.meetings = (List<Meeting>) meetings; }


    /**
     * Gets the total number of meetings suggested
     *
     * @return the number of meetings
     */
    public int getNumberOfMeetings() {
        return meetings.size();
    }


    private Meeting getMeeting(int meetingID) throws MeetingIDException{
        for (Meeting m : meetings) {
            if (m.getMeetingID() == meetingID) {
                return m;
            }
        }
        throw new MeetingIDException();
    }


    /** Initializes a new Meeting based on the given parameters. Return the MeetingID of the newly initialized Meeting.
     *
     * @param attendees The Attendees of the meeting
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return The MeetingID of the newly initialized Meeting.
     */
    public int newMeeting(List<String> attendees, String place, LocalDateTime time){
        Meeting m = new Meeting(getNumberOfMeetings() + 1, attendees, place, time);
        meetings.add(m);
        return m.getMeetingID();
    }


    /** Return the place at which the meeting (corresponding to the Meeting ID) will take place
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return The place where the meeting will occur
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public String getPlace(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).getPlace();
    }


    /** Return the time when the meeting (corresponding to the Meeting ID) will take place
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return The time when the meeting will take place
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public LocalDateTime getTime(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).getDateTime();
    }


    /** Return a list containing all the attendees of the meeting (corresponding to the Meeting ID)
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return A list containing all the attendees of this meeting
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public List<String> getAttendees(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).getAttendees();
    }


    /** Return whether or not the meeting (corresponding to the Meeting ID) has been accepted (by everyone)
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return Whether the meeting has been accepted
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public boolean isAccepted(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).isAccepted();
    }


    /** Return whether or not the meeting (corresponding to the Meeting ID) has been confirmed (by everyone)
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return Whether the meeting has been confirmed
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public boolean isConfirmed(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).isConfirmed();
    }


    /** Return a list containing all the attendees that haven't accepted the meeting (corresponding to the Meeting ID)
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return A list containing all the attendees that haven't accepted this meeting
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public List<String> getUnacceptedAttendees(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).getUnacceptedAttendees();
    }


    /** Return a list containing all the attendees that haven't confirmed the meeting (corresponding to the Meeting ID)
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @return A list containing all the attendees that haven't confirmed this meeting
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     */
    public List<String> getUnconfirmedAttendees(int meetingID) throws MeetingIDException{
        return getMeeting(meetingID).getUnacceptedAttendees();
    }


    /** Record the fact that the attendee has accepted the meeting (corresponding to the Meeting ID).
     * Return true if this change has been successfully accepted.
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @param attendee The attendee accepting the meeting
     * @return Whether this change has been recorded
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     * @throws WrongMeetingAccountException The attendee is not an actual attendee in the meeting
     */
    public boolean acceptMeeting(int meetingID, String attendee)
            throws MeetingIDException, WrongMeetingAccountException {
        Meeting m = getMeeting(meetingID);
        boolean b;
        try{b = m.acceptMeeting(attendee);}
        catch(IndexOutOfBoundsException e){throw new WrongMeetingAccountException();}
        if(b && m.isAccepted()){notifyAccepted(meetingID);}
        return b;
    }


    /** Record the fact that the attendee has confirmed the meeting (corresponding to the Meeting ID).
     * Return true if this change has been successfully accepted.
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @param attendee The attendee confirming the meeting
     * @return Whether this change has been recorded
     * @throws MeetingIDException Thrown if no Meeting corresponds to the given MeetingID
     * @throws WrongMeetingAccountException The attendee is not an actual attendee in the meeting
     * @throws TimeException The meeting isn't suppose to have taken place yet
     */
    public boolean confirmMeeting(int meetingID, String attendee)
            throws MeetingIDException, WrongMeetingAccountException, TimeException{
        Meeting m = getMeeting(meetingID);
        if(m.getDateTime().compareTo(LocalDateTime.now()) > 0){throw new TimeException();}
        boolean b;
        try{b = m.confirmMeeting(attendee);}
        catch(IndexOutOfBoundsException e){throw new WrongMeetingAccountException();}
        if(b && m.isConfirmed()){notifyConfirmed(meetingID);}
        return b;
    }




    //Meeting and Trade Observer Pattern below


    /** Add an observer to this subject/observed object
     *
     * @param meetingObserver The newly-added observer for this object
     */
    public void attachMeetingObserver(MeetingObserver meetingObserver){
        observers.add(meetingObserver);
    }


    /** Remove an observer from this subject/observed object
     *
     * @param meetingObserver The recently-removed observer from this object
     */
    public void detachMeetingObserver(MeetingObserver meetingObserver){
        observers.remove(meetingObserver);
    }


    /** Notify its observer that a meeting has been accepted
     *
     * @param meetingID The meeting that was accepted
     */
    public void notifyAccepted(int meetingID){
        for (MeetingObserver meetingObserver: observers) {
            meetingObserver.updateAccepted(meetingID);
        }
    }


    /** Notify its observers that a meeting has been confirmed
     *
     * @param meetingID The meeting that was confirmed
     */
    public void notifyConfirmed(int meetingID){
        for (MeetingObserver meetingObserver: observers) {
            meetingObserver.updateConfirmed(meetingID);
        }
    }
}
