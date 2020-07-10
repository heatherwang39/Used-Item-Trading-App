package main.java;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A child entity of Trade. This class represents a meeting between two people. The time and data of the meeting can
 * change a certain number of times, while the intended attendees cannot be changed.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
public class TwoPersonMeeting implements Serializable {
    private String place;
    private LocalDateTime time;
    private String firstAttendee;
    private String secondAttendee;
    private List<Boolean> accepted;
    private List<Boolean> confirmed;
    private int warnings;
    private int max_warnings = 6;

    /** Initialize a new instance of TwoPersonMeeting based on the given parameters
     *
     * @param firstAttendee The first Attendee of the meeting
     * @param secondAttendee The second Attendee of the meeting
     */
    public TwoPersonMeeting(String firstAttendee, String secondAttendee){
        this.firstAttendee = firstAttendee;
        this.secondAttendee = secondAttendee;

        accepted = new ArrayList();
        accepted.add(false);
        accepted.add(false);

        confirmed = new ArrayList();
        confirmed.add(false);
        confirmed.add(false);
    }



    /** Set the Meeting for this Trade to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the new time is inappropriate
     * @throws TradeCancelledException Thrown if the trade
     */
    public boolean setPlaceTime(String place, LocalDateTime time) throws TimeException, TradeCancelledException {
        if(getConfirmed()){
            return false;
        }
        if(time.compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        if(this.place.equals(place) && this.time.equals(time)){
            return false;
        }
        warnings += 1;
        if(warnings > max_warnings){
            throw new TradeCancelledException();
        }
        this.place = place;
        this.time = time;
        accepted.set(0, false);
        accepted.set(1, false);
        return true;
    }



    /** Suggest a place and time for this Meeting. The person suggesting this Meeting
     * automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will occur
     * @param suggester The person suggesting the place and time
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested time is inappropriate
     * @throws TradeCancelledException Thrown if the trade will be cancelled after this suggestion is made
     */
    public boolean suggestPlaceTime(String place, LocalDateTime time,
                                    String suggester) throws WrongAccountException,
            TimeException, TradeCancelledException {
        if(suggester.equals(firstAttendee) || suggester.equals(secondAttendee)){
            if(!setPlaceTime(place, time)){
                return false;
            }
            try{acceptMeeting(suggester);}
            catch(NoMeetingException e){return false;}
        }
        else{throw new WrongAccountException();}
        return true;
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
     * @throws NoMeetingException Thrown if no place and time have been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     */
    public boolean acceptMeeting(String attendee) throws NoMeetingException, WrongAccountException{
        if(place.isEmpty()){
            throw new NoMeetingException();
        }
        if(attendee.equals(firstAttendee)){
            if(accepted.get(0)){
                return false;
            }
            accepted.set(0, true);
            return true;
        }
        if(attendee.equals(secondAttendee)){
            if(accepted.get(1)){
                return false;
            }
            accepted.set(1, true);
            return true;
        }
        if(getAccepted()){
            resetWarnings();
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
     * @throws NoMeetingException Thrown if place and time have been agreed upon
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the meeting is supposed to take place in the future
     */
    public boolean confirmMeeting(String attendee) throws NoMeetingException, TimeException, WrongAccountException{
        if(!getAccepted()){
            throw new NoMeetingException();
        }
        if(time.compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        if(attendee.equals(firstAttendee)){
            if(confirmed.get(0)){
                return false;
            }
            confirmed.set(0, true);
            return true;
        }
        if(attendee.equals(secondAttendee)){
            if(confirmed.get(1)){
                return false;
            }
            confirmed.set(1, true);
            return true;
        }
        throw new WrongAccountException();
    }


    /** Return whether or not the Meeting has been accepted by both Attendees
     *
     * @return whether the Meeting has been accepted
     */
    public boolean getAccepted(){
        return accepted.get(0) && accepted.get(1);
    }


    /** Return whether or not the Meeting has been confirmed by both Attendees
     *
     * @return whether the Meeting has been confirmed
     */
    public boolean getConfirmed(){
        return confirmed.get(0) && confirmed.get(1);
    }


    /**Reset the number of warnings (i.e., the number of times a meeting has been
     * suggested without confirming) back to 0
     *
     */
    public void resetWarnings(){
        warnings = 0;
    }
}