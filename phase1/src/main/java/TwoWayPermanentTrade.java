package main.java;

import java.time.LocalDateTime;

public class TwoWayPermanentTrade extends TwoWayTrade implements OneMeeting {
    private TwoPersonMeeting meeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of TwoWayPermanentTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayPermanentTrade(String firstTrader, int firstItem,
                                String secondTrader, int secondItem){

        super(firstTrader, firstItem, secondTrader, secondItem);
        warnings = 0;
    }


    public String getMeetingPlace() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting.getPlace();
    }


    public LocalDateTime getMeetingTime() throws NoMeetingException{
        if(meeting == null){
            throw new NoMeetingException();
        }
        return meeting.getTime();
    }


    public boolean setMeeting(String place, LocalDateTime time) throws TimeException{
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getFirstTrader(), getSecondTrader());
        if(meeting.getTime().compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        this.meeting = meeting;
        return true;
    }


    public boolean suggestMeeting(String place, LocalDateTime time,
                                  String suggester) throws WrongAccountException, TimeException {

        if(suggester.equals(getFirstTrader()) || suggester.equals(getSecondTrader())){
            if(!setMeeting(place, time)){
                return false;
            }
            acceptMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    public boolean acceptMeeting(String acceptor) throws WrongAccountException {
        if(acceptor.equals(getFirstTrader()) || acceptor.equals(getSecondTrader())){
            meeting.acceptMeeting(acceptor);
        }
        throw new WrongAccountException();
    }

    public boolean confirmMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getFirstTrader()) || attendee.equals(getSecondTrader())){
            meeting.acceptMeeting(attendee);
        }
        throw new WrongAccountException();
    }

    /**Reset the number of warnings (i.e., the number of times a meeting has been
     * suggested without confirming) back to 0
     *
     */
    public void resetWarnings(){
        warnings = 0;
    }


    /** Returns whether or not the Trade is permanent. Iff the Trade is permanent, return true.
     *
     * @return whether the Trade is Permanent
     */
    public boolean isPermanent(){
        return true;
    }




    public boolean getMeetingAccepted(){
        return meeting.getAccepted();
    }


    public boolean getMeetingConfirmed(){
        return meeting.getConfirmed();
    }
}
