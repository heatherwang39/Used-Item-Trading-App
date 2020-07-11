package main.java;

import javax.security.auth.x500.X500PrivateCredential;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract class in the main.java.Trade system representing a trade in the program. All trades
 * store a trade number and a status, and there are associated items/traders for each trade.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class Trade implements Serializable, Entity {
    private final int tradeNumber;
    private int status;

    //Information regarding meetings are stored below

    private List<String> meetingPlaces;
    private List<LocalDateTime> meetingTimes;
    private List<List<String>> meetingAttendees;
    private List<List<Boolean>> meetingAccepted;
    private List<List<Boolean>> meetingConfirmed;
    private int warnings;
    private int maxWarnings;






    //Basic Trade Methods begin here







    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a HashMap
     */
    public HashMap<String, String> getData(){return new HashMap<>();}


    /** Initialize a new instance of Trade. The default status of the trade will be set to 0,
     * and the Trade will be given a unique tradeNumber.
     *
     */
    public Trade(int tradeNumber){
        this.tradeNumber = tradeNumber;
        status = 0;
        meetingPlaces = new ArrayList();
        meetingTimes = new ArrayList();
        meetingAttendees = new ArrayList();
        meetingAccepted = new ArrayList();
        meetingConfirmed = new ArrayList();
        maxWarnings = 6;
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






    //Methods regarding the before-and-after states of a User's inventory begin here






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






    //Methods regarding meetings begin here






    /** Return the place of the suggested meetingNumber-th Meeting for this Trade. If no Meeting has been suggested,
     * return null
     *
     * @param meetingNumber The meeting in which you're interested in
     * @return The place of the suggested meetingNumber-th Meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public String getMeetingPlace(int meetingNumber) throws MeetingNumberException{
        checkMeetingNumber(meetingNumber);
        return meetingPlaces.get(meetingNumber - 1);
    }

    /** Return the time of the suggested meetingNumber-th Meeting for this Trade. If no Meeting has been suggested,
     * return null
     *
     * @param meetingNumber The meeting in which you're interested in
     * @return The place of the suggested meetingNumber-th Meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public LocalDateTime getMeetingTime(int meetingNumber) throws MeetingNumberException{
        checkMeetingNumber(meetingNumber);
        return meetingTimes.get(meetingNumber - 1);
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
     * @throws TradeCancelledException Thrown if the trade has been cancelled due to the request or has previously been
     * cancelled
     */
    public boolean setMeeting(int meetingNumber, String place, LocalDateTime time)
            throws TimeException, MeetingNumberException, TradeCancelledException{

        //Check if the Trade Cancelled (Throws TradeCancelledException)

        checkTradeCancelled();

        //Check if the Meeting is already Confirmed

        if(getMeetingConfirmed(meetingNumber)){
            return false;
        }

        //Check if the time of the Meeting is before now

        if(time.compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }

        //Check if the previously suggested Meeting is at the same time as now

        if(getMeetingPlace(meetingNumber).equals(place) && getMeetingTime(meetingNumber).equals(time)){
            return false;
        }


        //Right now, the trade is set so that it will only cancel itself if the first meeting HASN'T been made
        //This may change in the future.

        if(meetingNumber == 1) {
            warnings += 1;
            if (warnings > maxWarnings) {
                setStatus(-1);
                throw new TradeCancelledException();
            }
        }
        meetingPlaces.set(meetingNumber - 1, place);
        meetingTimes.set(meetingNumber - 1, time);

        //Reset the status of the trade back to unaccepted and unconfirmed

        int i = 0;
        while(i < meetingAttendees.get(meetingNumber - 1).size()) {
            meetingAccepted.get(meetingNumber).set(i, false);
            meetingConfirmed.get(meetingNumber).set(i, false);
            i++;
        }
        return true;
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
     * @throws TradeCancelledException Thrown if the trade has been cancelled due to the request or has previously been
     * cancelled
     */
    public boolean suggestMeeting(int meetingNumber, String place, LocalDateTime time,
                                    String suggester) throws WrongAccountException, TimeException,
            MeetingNumberException, TradeCancelledException{
        boolean value = setMeeting(meetingNumber, place, time);
        try{acceptMeeting(meetingNumber, suggester);}
        catch(NoMeetingException e){}
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
     * @throws TradeCancelledException Thrown if the trade is cancelled
     */
    public boolean acceptMeeting(int meetingNumber, String acceptor) throws NoMeetingException, WrongAccountException,
            MeetingNumberException, TradeCancelledException{

        checkMeetingExists(meetingNumber);
        checkTradeCancelled();

        //Attempt to find the acceptor in the meeting

        List<String> users = meetingAttendees.get(meetingNumber - 1);
        int i = users.indexOf(acceptor);

        //If the acceptor to this meeting doesn't exist, throw a WrongAccountException

        if(i == -1){throw new WrongAccountException();}

        //Now we know the acceptor does exist. If the acceptor already accepted, return false

        List<Boolean> a = meetingAccepted.get(meetingNumber - 1);
        if(a.get(i)){
            return false;
        }

        //Now we know the acceptor should finish accepting. Finish recording, and return true

        a.set(i, true);
        return true;
    }


    /** Attempt to record the fact that attendee has confirmed the accepted meetingNumber-th Meeting. If this fact is
     * successfully recorded, return True.
     *
     * @param meetingNumber The meeting that is trying to be confirmed
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been accepted
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the Meeting was confirmed before it was supposed to take place
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     * @throws TradeCancelledException Thrown if the trade is cancelled
     */
    public boolean confirmMeeting(int meetingNumber, String attendee) throws NoMeetingException,
            WrongAccountException, TimeException, MeetingNumberException, TradeCancelledException{

        checkMeetingExists(meetingNumber);
        checkTradeCancelled();

        //If the meeting hasn't been accepted, throw the NoMeetingException

        if(!getMeetingAccepted(meetingNumber)){throw new NoMeetingException();}

        //If the meeting isn't supposed to have happened yet, throw the TimeException.

        if(getMeetingTime(meetingNumber).compareTo(LocalDateTime.now()) > 0){
            throw new TimeException();
        }

        //Try to find the person attempting to confirm the meeting

        List<String> users = meetingAttendees.get(meetingNumber - 1);
        int i = users.indexOf(attendee);
        if(i == -1){throw new WrongAccountException();}

        //

        List<Boolean> a = meetingConfirmed.get(meetingNumber - 1);
        if(a.get(i)){
            return false;
        }
        a.set(i, true);

        //If this is the last meeting and this meeting has been confirmed, finish the trade

        if(getMeetingConfirmed(meetingNumber) && meetingNumber == getNumMeetings()){
            setStatus(2);
        }
        return true;
    }


    /** Return True iff the meetingNumber-th Meeting has been accepted
     *
     * @param meetingNumber The meeting that you're trying to obtain information about
     * @return whether the Meeting has been accepted.
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean getMeetingAccepted(int meetingNumber) throws MeetingNumberException{
        checkMeetingNumber(meetingNumber);
        List<Boolean> meeting = meetingAccepted.get(meetingNumber - 1);
        for(Boolean v : meeting){
            if(!v){
                return false;
            }
        }
        return true;
    }


    /** Return True iff the meetingNumber-th Meeting has been confirmed
     *
     * @param meetingNumber The meeting that you're trying to obtain information about
     * @return whether the Meeting has been confirmed.
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    public boolean getMeetingConfirmed(int meetingNumber) throws MeetingNumberException{
        checkMeetingNumber(meetingNumber);
        List<Boolean> meeting = meetingConfirmed.get(meetingNumber - 1);
        for(Boolean v : meeting){
            if(!v){
                return false;
            }
        }
        return true;
    }


    /** Returns the number of meetings that will occur over the course of the trade
     *
     * @return The number of meetings that will occur over the course of the trade
     */
    public int getNumMeetings(){
        return meetingPlaces.size();
    }


    /** Resets the Warnings for meetings to 0.
     *
     */
    public void resetWarnings(){
        warnings = 0;
    }






    //Protected and private Methods are below.






    /** Adds another required meeting for this trade involving the attendees
     *
     * @param attendees A list containing the attendees that must attend the Trade.
     */
    protected void newMeeting(List<String> attendees){
        meetingPlaces.add(null);

        meetingTimes.add(null);

        List<String> copy = new ArrayList(attendees);
        meetingAttendees.add(copy);

        List<Boolean> accepted = new ArrayList();
        accepted.add(false);
        accepted.add(false);
        meetingAccepted.add(accepted);

        List<Boolean> confirmed = new ArrayList();
        confirmed.add(false);
        confirmed.add(false);
        meetingConfirmed.add(confirmed);
    }


    private void checkMeetingNumber(int meetingNumber) throws MeetingNumberException{
        if(!(1 <= meetingNumber && meetingNumber <= getNumMeetings())){
            throw new MeetingNumberException();
        }
    }


    private void checkMeetingExists(int meetingNumber) throws NoMeetingException, MeetingNumberException{
        if(getMeetingPlace(meetingNumber) == null && getMeetingTime(meetingNumber) == null){
            throw new NoMeetingException();
        }
    }


    private void checkTradeCancelled() throws TradeCancelledException{
        if(getStatus() == -1){
            throw new TradeCancelledException();
        }
    }
}