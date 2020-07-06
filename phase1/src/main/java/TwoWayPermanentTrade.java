package main.java;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TwoWayPermanentTrade extends TwoWayTrade implements OneMeeting, Serializable {
    private TwoPersonMeeting meeting;


    /** Initializes an instance of TwoWayPermanentTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayPermanentTrade(int tradeNumber, String firstTrader, int firstItem,
                                String secondTrader, int secondItem){

        super(tradeNumber, firstTrader, firstItem, secondTrader, secondItem);
        meeting = new TwoPersonMeeting(firstTrader, secondTrader);
    }


    /** Return the place of the suggested Meeting for this Trade. If no Meeting has been suggested, return null
     *
     * @return The place of the suggested Meeting
     */
    public String getMeetingPlace(){
        return meeting.getPlace();
    }


    /** Return the time of the suggested Meeting for this Trade. If no Meeting has been suggested, return null
     *
     * @return The time of the suggested Meeting
     */
    public LocalDateTime getMeetingTime(){
        return meeting.getTime();
    }


    /** Set the Meeting for this Trade to be at this place and this time. This Meeting will still need
     * to be confirmed. Return True if the meeting was successfully set.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @return Whether or not the change was successfully made
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean setMeeting(String place, LocalDateTime time) throws TimeException{
        boolean value;
        try{ value = meeting.setPlaceTime(place, time);}
        catch(TradeCancelledException e){
            setStatus(-1);
            return false;
        }
        return value;
    }


    /** Suggest a Meeting for this Trade to be at this place and this time. The person suggesting this Meeting
     * automatically accepts this Meeting. Return True if the change was successfully made.
     *
     * @param place The place where the meeting will take place
     * @param time The time where the meeting will take place
     * @param suggester The person suggesting the meeting
     * @return Whether or not the suggestion was successfully recorded
     * @throws WrongAccountException Thrown if the suggester is not supposed to be part of the Meeting
     * @throws TimeException Thrown if the suggested Meeting is at an invalid time
     */
    public boolean suggestMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {
        boolean value;
        try{value = meeting.suggestPlaceTime(place, time, suggester);}
        catch(TradeCancelledException e){
            setStatus(-1);
            return false;
        }
        return value;
    }


    /** Attempt to record the fact that acceptor has accepted the suggested Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param acceptor The attendee that is agreeing to a suggested Meeting
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the acceptor has not been invited to this meeting
     */
    public boolean acceptMeeting(String acceptor) throws NoMeetingException, WrongAccountException {
        return meeting.acceptMeeting(acceptor);
    }


    /** Attempt to record the fact that attendee has confirmed the suggested Meeting. If this fact is successfully
     * recorded, return True.
     *
     * @param attendee The attendee confirming that the Meeting has happened
     * @return Whether the change has been successfully recorded
     * @throws NoMeetingException Thrown if no meeting has been suggested
     * @throws WrongAccountException Thrown if the attendee was not been invited to this meeting
     * @throws TimeException Thrown if the Meeting was confirmed before it was supposed to take place
     */
    public boolean confirmMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException{
        boolean value;
        value = meeting.confirmMeeting(attendee);
        if(meeting.getConfirmed()){
            setStatus(2);
        }
        return value;
    }


    /**Reset the number of warnings (i.e., the number of times a meeting has been
     * suggested without confirming) back to 0
     *
     */
    public void resetWarnings(){
        meeting.resetWarnings();
    }

    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    public boolean isPermanent(){
        return true;
    }


    /** Return True iff the Meeting corresponding to the Trade has been accepted
     *
     * @return whether the Meeting has been accepted.
     */
    public boolean getMeetingAccepted(){
        return meeting.getAccepted();
    }


    /** Return True iff the Meeting corresponding to the Trade has been confirmed
     *
     * @return whether the Meeting has been confirmed.
     */
    public boolean getMeetingConfirmed(){
        return meeting.getConfirmed();
    }


    /** Returns a List with a length equal to that of the number of traders involved in the trade. At each index,
     * store the ID of the item that is involved in the trade and will be own by the Trader at the given
     * index in getTraders() at the end of the trade. Iff the specified Trader will not receive an item that at the end
     * of the trade, store null at that particular index.
     *
     * @return A list of item IDs involved in this trade based on the original owners
     */
    public List<Integer> getItemsFinal(){
        List<Integer> items = new ArrayList();
        items.add(getSecondTraderItem());
        items.add(getFirstTraderItem());
        return items;
    }
}
