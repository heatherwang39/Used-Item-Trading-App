package main.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TwoPersonMeeting implements Meeting {
    private String place;
    private LocalDateTime time;
    private String firstAttendee;
    private String secondAttendee;
    private List<Boolean> Accepted;
    private List<Boolean> Confirmed;

    /** Initialize a new instance of TwoPersonMeeting based on the given parameters
     *
     * @param place The place where this meeting will take place
     * @param time The time when this meeting will take place
     * @param firstAttendee The first Attendee of the meeting
     * @param secondAttendee The second Attendee of the meeting
     */
    public TwoPersonMeeting(String place, LocalDateTime time, String firstAttendee, String secondAttendee){
        this.place = place;
        this.time = time;
        this.firstAttendee = firstAttendee;
        this.secondAttendee = secondAttendee;

        Accepted = new ArrayList();
        Accepted.add(false);
        Accepted.add(false);

        Confirmed = new ArrayList();
        Confirmed.add(false);
        Confirmed.add(false);
    }

    /** Return the String representation of the location of this Meeting (i.e., Address)
     *
     * @return The String representation of the location of this Meeting
     */
    public String getPlace(){
        return place;
    }


    /** Return the time at which this Meeting will take place
     *
     * @return The time at which this Meeting will take place
     */
    public LocalDateTime getTime(){
        return time;
    }


    /** Return a List of Attendees for this Meeting
     *
     * @return A list of Attendees for this Meeting
     */
    public List<String> getAttendees(){
        List attendees = new ArrayList();
        attendees.add(firstAttendee);
        attendees.add(secondAttendee);
        return attendees;
    }

    /** Record that the meeting has been accepted by the given attendee.
     * Return True if this change has successfully been recorded
     * Throws WrongAccountException iff the given attendee is not actually an attendee
     * for this meeting.
     *
     * @param attendee The attendee that is accepting the meeting took place
     * @return Whether the change has been successfully recorded
     * @throws WrongAccountException
     */
    public boolean acceptMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(firstAttendee)){
            if(Accepted.get(0)){
                return false;
            }
            Accepted.set(0, true);
            return true;
        }
        if(attendee.equals(secondAttendee)){
            if(Accepted.get(1)){
                return false;
            }
            Accepted.set(1, true);
            return true;
        }
        throw new WrongAccountException();
    }

    /** Record that the meeting has been confirmed by the given attendee.
     * Return True if this change has successfully been recorded
     * Throws WrongAccountException iff the given attendee is not actually an attendee
     * for this meeting.
     *
     * @param attendee The attendee that is confirming the meeting took place
     * @return Whether the change has been successfully recorded
     * @throws WrongAccountException
     */
    public boolean confirmMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(firstAttendee)){
            if(Confirmed.get(0)){
                return false;
            }
            Confirmed.set(0, true);
            return true;
        }
        if(attendee.equals(secondAttendee)){
            if(Confirmed.get(1)){
                return false;
            }
            Confirmed.set(1, true);
            return true;
        }
        throw new WrongAccountException();
    }


    /** Return whether or not the Meeting has been accepted by both Attendees
     *
     * @return whether the Meeting has been accepted
     */
    public boolean getAccepted(){
        return Accepted.get(0) && Accepted.get(1);
    }

    /** Return whether or not the Meeting has been confirmed by both Attendees
     *
     * @return whether the Meeting has been confirmed
     */
    public boolean getConfirmed(){
        return Confirmed.get(0) && Confirmed.get(1);
    }
}