package main.java;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class in the main.java.Trade system representing a trade in the program. All trades
 * store a trade number and a status, and there are associated items/traders for each trade.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class Trade implements Serializable {
    private final int tradeNumber;
    private int status;
    private List<TwoPersonMeeting> meetings;

    /** Initialize a new instance of Trade. The default status of the trade will be set to 0,
     * and the Trade will be given a unique tradeNumber.
     *
     */
    public Trade(int tradeNumber){
        this.tradeNumber = tradeNumber;
        status = 0;
    }


    /**
     * Return the trade number of the main.java.Trade object
     *
     * @return the trade number of the main.java.Trade object
     */
    public int getTradeNumber(){
        return tradeNumber;
    }

    /**
     * Return the status of the main.java.Trade Object.
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade is ongoing
     * 2 represents that the trade has been completed
     *
     * @return the status of the main.java.Trade Object.
     */
    public int getStatus(){
        return status;
    }


    /**
     * Changes the status of the main.java.Trade object. Iff the change was successfully made, return True.
     *
     * -1 represents that the trade has been cancelled
     * 0 represents that the trade is awaiting confirmation
     * 1 represents that the trade is ongoing
     * 2 represents that the trade has been completed
     *
     * @param status The new status of the main.java.Trade
     * @return A boolean representing whether or not the change was made
     */
    public boolean setStatus(int status){
        if(this.status == status){
            return false;
        }
        this.status = status;
        return true;
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    abstract boolean isPermanent();


    /** Returns whether or not the Trade is one-way. Iff the Trade is one-way, return true.
     *
     * @return whether the Trade is one-way
     */
    abstract boolean isOneWay();


    /** Returns a List of Traders (i.e., their usernames) involved in this trade
     *
     * @return Usernames of Traders involved in this trade
     */
    abstract List<String> getTraders();


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and was originally owned by the Trader at the given
     * index in getTraders(). Iff the specified Trader has no item that he/she is involving in trade and originally
     * owned, store null at the particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    abstract List<Integer> getItemsOriginal();


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and will be own by the Trader at the given
     * index in getTraders() at the end of the trade. Iff the specified Trader will not receive an item that at the end
     * of the trade, store null at that particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    abstract List<Integer> getItemsFinal();


    /** Sets the meetings in Trade to be equal to this. Each meeting
     *
     * @param meetings What you want to set the meetings in Trade to be equal to.
     */
    protected void setMeetings(List<TwoPersonMeeting> meetings){
        this.meetings = meetings;
    }


    /** Return the place of the suggested meetingNumber-th Meeting for this Trade. If no Meeting has been suggested,
     * return null
     *
     * @param meetingNumber The meeting in which you're interested in
     * @return The place of the suggested meetingNumber-th Meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public String getMeetingPlace(int meetingNumber) throws MeetingNumberException{
        return getMeeting(meetingNumber).getPlace();
    }

    /** Return the time of the suggested meetingNumber-th Meeting for this Trade. If no Meeting has been suggested,
     * return null
     *
     * @param meetingNumber The meeting in which you're interested in
     * @return The place of the suggested meetingNumber-th Meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public LocalDateTime getMeetingTime(int meetingNumber) throws MeetingNumberException{
        return getMeeting(meetingNumber).getTime();
    }


    /** Set the meetingNumber-th Meeting for this Trade to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param meetingNumber The meeting you're going to set
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean setMeeting(int meetingNumber, String place, LocalDateTime time,
                                String suggester) throws TimeException, MeetingNumberException{
        boolean value;
        try{value = getMeeting(meetingNumber).setPlaceTime(place, time);}
        catch(TradeCancelledException e){
            setStatus(-1);
            return false;
        }
        return value;
    }


    /** Suggest that the meetingNumber-th Meeting for this Trade to be at this place and this time. The person
     * suggesting this Meeting automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param meetingNumber The meeting you're going to set
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean suggestMeeting(int meetingNumber, String place, LocalDateTime time,
                                    String suggester) throws WrongAccountException, TimeException,
            MeetingNumberException{
        boolean value;
        try{value = getMeeting(meetingNumber).suggestPlaceTime(place, time, suggester);}
        catch(TradeCancelledException e){
            if(meetingNumber == 1){
            setStatus(-1);
            }else{
                resetWarnings();
            }
            return false;
        }
        return value;
    }


    /** Attempt to record the fact that acceptor has accepted the suggested meetingNumber-th Meeting. If this fact is
     * successfully recorded, return True.
     *
     * @param meetingNumber The meeting that is trying to be accepted
     * @param acceptor The attendee that is agreeing to a suggested Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean acceptMeeting(int meetingNumber, String acceptor) throws NoMeetingException, WrongAccountException,
            MeetingNumberException{
        return getMeeting(meetingNumber).acceptMeeting(acceptor);
    }


    /** Attempt to record the fact that attendee has confirmed the suggested meetingNumber-th Meeting. If this fact is
     * successfully recorded, return True.
     *
     * @param meetingNumber The meeting that is trying to be confirmed
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the Meeting was confirmed before it was supposed to take place
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean confirmMeeting(int meetingNumber, String attendee) throws NoMeetingException,
            WrongAccountException, TimeException, MeetingNumberException{
        boolean value;
        value = getMeeting(meetingNumber).confirmMeeting(attendee);
        if(meetingNumber == getNumMeetings()){
                if(getMeeting(meetingNumber).getConfirmed()){
                    setStatus(2);
                }
        }
        return value;
    }


    /** Return True iff the meetingNumber-th Meeting has been accepted
     *
     * @param meetingNumber The meeting that you're trying to obtain information about
     * @return whether the Meeting has been accepted.
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean getMeetingAccepted(int meetingNumber) throws MeetingNumberException{
        return getMeeting(meetingNumber).getAccepted();
    }


    /** Return True iff the meetingNumber-th Meeting has been confirmed
     *
     * @param meetingNumber The meeting that you're trying to obtain information about
     * @return whether the Meeting has been confirmed.
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean getMeetingConfirmed(int meetingNumber) throws MeetingNumberException{
        return getMeeting(meetingNumber).getConfirmed();
    }


    /** Returns the number of meetings that will occur over the course of the trade
     *
     * @return The number of meetings that will occur over the course of the trade
     */
    public int getNumMeetings(){
        return meetings.size();
    }

    private TwoPersonMeeting getMeeting(int meetingNumber) throws MeetingNumberException{
        if(1 <= meetingNumber && meetingNumber <= getNumMeetings()){
            return meetings.get(meetingNumber - 1);
        }
        throw new MeetingNumberException();
    }

    /** Resets the Warnings for all of the meetings to 0.
     *
     */
    public void resetWarnings(){
        int i;
        i = 1;
        while(i <= getNumMeetings()){
            try{getMeeting(i).resetWarnings();}
            catch(MeetingNumberException e){}
            i++;
        }
    }





    /**
     * An inner class of Trade. This class represents a meeting between two people. The time and data of the meeting can
     * change a certain number of times, while the intended attendees cannot be changed.
     * @author Warren Zhu
     * @version %I%, %G%
     * @since Phase 1
     */
    protected class TwoPersonMeeting implements Serializable {
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
}
