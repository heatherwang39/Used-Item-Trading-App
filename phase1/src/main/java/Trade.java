package main.java;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An abstract class in the main.java.Trade system representing a trade in the program. All trades
 * store a trade number and a status, and there are associated items/traders for each trade.
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 1
 */
abstract class Trade implements Serializable {
    private int tradeNumber;
    private int status;

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






    /** Return the place of the suggested meetingNumber-th Meeting for this Trade. If no Meeting has been suggested,
     * return null
     *
     * @param meetingNumber The meeting in which you're interested in
     * @return The place of the suggested meetingNumber-th Meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    abstract String getMeetingPlace(int meetingNumber) throws MeetingNumberException;

    /** Return the time of the suggested meetingNumber-th Meeting for this Trade. If no Meeting has been suggested,
     * return null
     *
     * @param meetingNumber The meeting in which you're interested in
     * @return The place of the suggested meetingNumber-th Meeting
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    abstract LocalDateTime getMeetingTime(int meetingNumber) throws MeetingNumberException;


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
    abstract boolean setMeeting(int meetingNumber, String place, LocalDateTime time,
                                String suggester) throws TimeException, MeetingNumberException;


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
    abstract boolean suggestMeeting(int meetingNumber, String place, LocalDateTime time,
                                    String suggester) throws WrongAccountException, TimeException,
            MeetingNumberException;


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
    abstract boolean acceptMeeting(int meetingNumber, String acceptor) throws NoMeetingException, WrongAccountException,
            MeetingNumberException;


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
    abstract boolean confirmMeeting(int meetingNumber, String attendee) throws NoMeetingException,
            WrongAccountException, TimeException, MeetingNumberException;


    /** Return True iff the meetingNumber-th Meeting has been accepted
     *
     * @param meetingNumber The meeting that you're trying to obtain information about
     * @return whether the Meeting has been accepted.
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    abstract boolean getMeetingAccepted(int meetingNumber) throws MeetingNumberException;


    /** Return True iff the meetingNumber-th Meeting has been confirmed
     *
     * @param meetingNumber The meeting that you're trying to obtain information about
     * @return whether the Meeting has been confirmed.
     * @throws MeetingNumberException Thrown when the meetingNumber-th isn't supposed to occur
     */
    abstract boolean getMeetingConfirmed(int meetingNumber) throws MeetingNumberException;

    /** Returns the number of meetings that will occur over the course of the trade
     *
     * @return The number of meetings that will occur over the course of the trade
     */
    abstract int getNumMeetings();
}
