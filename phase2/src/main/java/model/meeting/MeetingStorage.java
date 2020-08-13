package main.java.model.meeting;

import main.java.model.Storage;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Use case class for initializing, storing, recording changes and retrieving information regarding Meeting.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public class MeetingStorage  implements Storage, MeetingObservee{
    private List<Meeting> meetings;
    private final List<MeetingObserver> observers = new ArrayList<>();

    @Override
    public Object getNewStorageData() {
        return new ArrayList<Meeting>();
    }

    @Override
    public void setStorageData(Object meetings) {
        this.meetings = (List<Meeting>) meetings;
    }


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

    /**
     * Gets all relevant data for a meeting with given meetingID in a HashMap format. The respective keys and values:
     * (1) id: id of Meeting (number, single element)
     * (2) place: location of meeting (name, single element)
     * (3) datetime: date and time of meeting in form of “dow mon dd hh:mm:ss zzz yyy” (name, single element)
     * (4) attendees: usernames of users participating in meeting (names, two or more elements)
     * (5) unaccepted: usernames of users who have not accepted meeting yet (names, zero or more elements)
     * (6) unconfirmed: usernames of users who have not confirmed meeting yet (names, zero or more elements)
     *
     * @param meetingID the meetingID of the Meeting getting data of
     * @return HashMap with all relevant data for a Meeting
     * @throws MeetingIDException Thrown if no Meeting has the given meetingID
     */
    public HashMap<String, List<String>> getMeetingData(int meetingID) throws MeetingIDException {
        HashMap<String, List<String>> meetingData = new HashMap<>();
        Meeting meeting = getMeeting(meetingID);
        meetingData.put("id", Collections.singletonList(String.valueOf(meeting.getMeetingID())));
        meetingData.put("place", Collections.singletonList(meeting.getPlace()));
        meetingData.put("datetime", Collections.singletonList(meeting.getDateTime().toString()));
        meetingData.put("attendees", meeting.getAttendees());
        meetingData.put("unaccepted", meeting.getUnacceptedAttendees());
        meetingData.put("unconfirmed", meeting.getUnconfirmedAttendees());
        return meetingData;
    }

    /**
     * Gets all relevant data for all meetings in given list of meetingNumbers in HashMap format
     *
     * @param meetings list of meetingNumbers of the Meetings getting data of
     * @return List of HashMaps, with each element being the data of a Meeting
     * @throws MeetingIDException Thrown if no Meeting has one of the given meetingNumbers
     */
    public List<HashMap<String, List<String>>> getMeetingsData(List<Integer> meetings) throws MeetingIDException {
        List<HashMap<String, List<String>>> meetingsData = new ArrayList<>();
        for (Integer meetingNumber: meetings) {
            meetingsData.add(getMeetingData(meetingNumber));
        }
        return meetingsData;
    }


    /** Return the IDs of all the meetings that the given participant is involved with and hasn't been accepted yet
     *
     * @param participant The participant that is involved with said meetings
     * @return A list of MeetingIDs of meetings with the given property
     */
    public List<Integer> getSuggestedMeetings(String participant){
        List<Integer> meeting = new ArrayList<>();
        for(Meeting m: meetings){
            if(m.isNotCancelled() && !m.isAccepted()){
                if(m.getAttendees().contains(participant) && m.getUnacceptedAttendees().contains(participant)){
                    meeting.add(m.getMeetingID());
                }
            }
        }
        return meeting;
    }

    /** Return the IDs of all the meetings that the given participant is involved with and has been accepted but not
     * confirmed yet
     *
     * @param participant participant in meeting
     * @return A list of MeetingIDs of meetings with the given property
     */
    public List<Integer> getOngoingMeetings(String participant){
        List<Integer> meeting = new ArrayList<>();
        for(Meeting m: meetings){
            if(m.isNotCancelled()){
                if(m.isAccepted() && !m.isConfirmed()){
                    if(m.getAttendees().contains(participant) && m.getUnconfirmedAttendees().contains(participant)){
                        meeting.add(m.getMeetingID());
                    }
                }
            }
        }
        return meeting;
    }

    /** Return the IDs of all the meetings that the given participant is involved with and has been confirmed
     *
     * @param participant participant in meeting
     * @return A list of MeetingIDs of meetings with the given property
     */
    public List<Integer> getCompletedMeetings(String participant){
        List<Integer> meeting = new ArrayList<>();
        for(Meeting m: meetings){
            if(m.isNotCancelled()){
                if(m.isConfirmed()){
                    if(m.getAttendees().contains(participant)){
                        meeting.add(m.getMeetingID());
                    }
                }
            }
        }
        return meeting;
    }

    /** Cancel the meeting associated with the given ID.
     *
     * @param meetingID The meetingID of the meeting you're interested in
     * @throws MeetingIDException Thrown when no meeting corresponds to the given meeting ID
     * @throws MeetingAlreadyConfirmedException Thrown when the meeting has already been completed
     */
    public void cancelMeeting(int meetingID) throws MeetingIDException, MeetingAlreadyConfirmedException{
        Meeting m = getMeeting(meetingID);
        if(m.isConfirmed()){
            throw new MeetingAlreadyConfirmedException();
        }
        if(m.cancel()){
            notifyCancelled(meetingID);
        }
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

    /** Record the fact that the given meeting has been cancelled
     *
     * @param meetingID The ID of the cancelled meeting
     */
    public void notifyCancelled(int meetingID){
        for (MeetingObserver meetingObserver: observers) {
            meetingObserver.updateCancelled(meetingID);
        }
    }
}
