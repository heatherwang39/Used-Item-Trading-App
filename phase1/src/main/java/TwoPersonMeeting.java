package main.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TwoPersonMeeting implements Meeting {
    private String place;
    private LocalDateTime time;
    private Account firstAttendee;
    private Account secondAttendee;
    private List<Boolean> Accepted;
    private List<Boolean> Confirmed;


    public TwoPersonMeeting(String place, LocalDateTime time, Account firstAttendee, Account secondAttendee){
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


    public String getPlace(){
        return place;
    }


    public LocalDateTime getTime(){
        return time;
    }


    public List<Account> getAttendees(){
        List attendees = new ArrayList();
        attendees.add(firstAttendee);
        attendees.add(secondAttendee);
        return attendees;
    }


    public boolean acceptMeeting(Account attendee) throws WrongAccountException{
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


    public boolean confirmMeeting(Account attendee) throws WrongAccountException{
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


    public boolean getAccepted(){
        return Accepted.get(0) && Accepted.get(1);
    }


    public boolean getConfirmed(){
        return Confirmed.get(0) && Confirmed.get(1);
    }
}