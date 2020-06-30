package main.java;

import java.time.LocalDateTime;

public class TwoWayTemporaryTrade extends TwoWayTrade implements TwoMeetings {
    private TwoPersonMeeting firstMeeting;
    private TwoPersonMeeting secondMeeting;
    private int warnings;
    private int max_warnings = 6;


    /** Initializes an instance of TwoWayTemporaryTrade based on the given parameters
     *
     * @param firstTrader The first trader (their username) involved in this trade
     * @param firstItem The item (its ID) the first trader traded away
     * @param secondTrader The second trader (their username) involved in this trade
     * @param secondItem The item (its ID) the second trader traded away
     */
    public TwoWayTemporaryTrade(String firstTrader, int firstItem,
                                String secondTrader, int secondItem) {

        super(firstTrader, firstItem, secondTrader, secondItem);
        warnings = 0;
    }


    public String getFirstMeetingPlace() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getPlace();
    }


    public String getSecondMeetingPlace() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getPlace();
    }


    public LocalDateTime getFirstMeetingTime() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return firstMeeting.getTime();
    }


    public LocalDateTime getSecondMeetingTime() throws NoMeetingException{
        if(firstMeeting == null){
            throw new NoMeetingException();
        }
        return secondMeeting.getTime();
    }


    public boolean setFirstMeeting(String place, LocalDateTime time) throws TimeException{
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getFirstTrader(), getSecondTrader());
        if(meeting.getTime().compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        firstMeeting = meeting;
        return true;
    }


    public boolean setSecondMeeting(String place, LocalDateTime time) throws TimeException{
        TwoPersonMeeting meeting = new TwoPersonMeeting(place, time, getFirstTrader(), getSecondTrader());
        if(meeting.getTime().compareTo(LocalDateTime.now()) < 0){
            throw new TimeException();
        }
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        secondMeeting = meeting;
        return true;
    }


    public boolean suggestFirstMeeting(String place, LocalDateTime time,
                                       String suggester) throws WrongAccountException, TimeException {

        if(suggester.equals(getFirstTrader()) || suggester.equals(getSecondTrader())){
            if(!setFirstMeeting(place, time)){
                return false;
            }
            acceptFirstMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    public boolean suggestSecondMeeting(String place, LocalDateTime time,
                                        String suggester) throws WrongAccountException, TimeException {

        if(suggester.equals(getFirstTrader()) || suggester.equals(getSecondTrader())){
            if(!setSecondMeeting(place, time)){
                return false;
            }
            acceptSecondMeeting(suggester);
        }
        else{throw new WrongAccountException();}
        return true;
    }


    public boolean acceptFirstMeeting(String acceptor) throws WrongAccountException {
        if(acceptor.equals(getFirstTrader()) || acceptor.equals(getSecondTrader())){
            firstMeeting.acceptMeeting(acceptor);
        }
        throw new WrongAccountException();
    }


    public boolean acceptSecondMeeting(String acceptor) throws WrongAccountException {
        if(acceptor.equals(getFirstTrader()) || acceptor.equals(getSecondTrader())){
            secondMeeting.acceptMeeting(acceptor);
        }
        throw new WrongAccountException();
    }


    public boolean confirmFirstMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getFirstTrader()) || attendee.equals(getSecondTrader())){
            firstMeeting.acceptMeeting(attendee);
        }
        throw new WrongAccountException();
    }


    public boolean confirmSecondMeeting(String attendee) throws WrongAccountException{
        if(attendee.equals(getFirstTrader()) || attendee.equals(getSecondTrader())){
            secondMeeting.acceptMeeting(attendee);
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
        return false;
    }


    public boolean getFirstMeetingAccepted(){
        return firstMeeting.getAccepted();
    }


    public boolean getSecondMeetingAccepted(){
        return secondMeeting.getAccepted();
    }


    public boolean getFirstMeetingConfirmed(){
        return firstMeeting.getConfirmed();
    }


    public boolean getSecondMeetingConfirmed(){
        return secondMeeting.getConfirmed();
    }
}
