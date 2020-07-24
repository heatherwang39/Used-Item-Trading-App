package main.java.Transactions.Meeting;

import main.java.Transactions.WrongAccountException;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingStorage {
    private List<Meeting> meetings;


    public MeetingStorage(List<Meeting> meetings){
        this.meetings = meetings;
    }


    /**
     * Gets the total number of meetings suggested
     *
     * @return the number of meetings
     */
    public int getNumberOfMeetings() {
        return meetings.size();
    }


    private Meeting getMeeting(int meetingID) throws MeetingNumberException {
        for (Meeting m : meetings) {
            if (m.getmeetingID() == meetingID) {
                return m;
            }
        }
        throw new MeetingNumberException();
    }


    public int NewMeeting(List<String> attendees, String place, LocalDateTime time){
        Meeting m = new Meeting(getNumberOfMeetings() + 1, attendees, place, time);
        meetings.add(m);
        return m.getmeetingID();
    }


    public String getPlace(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).getPlace();
    }

    public LocalDateTime getTime(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).getTime();
    }


    public List<String> getAttendees(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).getAttendees();
    }

    public boolean isAccepted(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).isAccepted();
    }

    public boolean isConfirmed(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).isConfirmed();
    }

    public List<String> getUnacceptedAttendees(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).getUnacceptedAttendees();
    }

    public List<String> getUnconfirmedAttendees(int meetingID) throws MeetingNumberException{
        return getMeeting(meetingID).getUnacceptedAttendees();
    }

    public boolean acceptMeeting(int meetingID, String attendee)
            throws MeetingNumberException, WrongAccountException {
        Meeting m = getMeeting(meetingID);
        boolean b;
        try{b = m.acceptMeeting(attendee);}
        catch(IndexOutOfBoundsException e){throw new WrongAccountException();}
        return b;
    }

    public boolean confirmMeeting(int meetingID, String attendee)
            throws MeetingNumberException, WrongAccountException, TimeException{
        Meeting m = getMeeting(meetingID);
        if(m.getTime().compareTo(LocalDateTime.now()) > 0){throw new TimeException();}
        boolean b;
        try{b = m.confirmMeeting(attendee);}
        catch(IndexOutOfBoundsException e){throw new WrongAccountException();}
        return b;
    }
}
