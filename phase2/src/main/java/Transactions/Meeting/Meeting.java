package main.java.Transactions.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Meeting {
    private int meetingID;
    private String place;
    private LocalDateTime time;
    private List<String> attendees;
    private List<Boolean> accepted;
    private List<Boolean> confirmed;


    //Initializer


    public Meeting(int meetingID, List<String> attendees, String place, LocalDateTime time){
        this.meetingID = meetingID;
        this.place = place;
        this.time = time;

        this.attendees = attendees;
        accepted = new ArrayList();
        confirmed = new ArrayList();
        int i = 0;
        while(i < attendees.size()) {
            accepted.add(false);
            confirmed.add(false);
        }
    }


    //Getters


    public int getmeetingID() {
        return meetingID;
    }


    public String getPlace(){
        return place;
    }


    public LocalDateTime getTime(){
        return time;
    }


    public List<String> getAttendees(){
        List<String> attendeesCopy = new ArrayList(attendees);
        return attendeesCopy;
    }


    public boolean isAccepted(){
        for(Boolean a: accepted){
            if(!a){
                return false;
            }
        }
        return true;
    }


    public boolean isConfirmed(){
        for(Boolean a: accepted){
            if(!a){
                return false;
            }
        }
        return true;
    }


    public List<String> getUnacceptedAttendees(){
        List Unaccepted = new ArrayList();
        int i = 0;
        while(i < attendees.size()){
            if(!accepted.get(i)){
                Unaccepted.add(attendees.get(i));
            }
        }
        return Unaccepted;
    }


    public List<String> getUnconfirmedAttendees(){
        List unconfirmed = new ArrayList();
        int i = 0;
        while(i < attendees.size()){
            if(!accepted.get(i)){
                unconfirmed.add(attendees.get(i));
            }
        }
        return unconfirmed;
    }


    //Pseudo-Setters


    public boolean acceptMeeting(String attendee){
        int i = attendees.indexOf(attendee);
        if(!accepted.get(i)){
            accepted.set(i, true);
            return true;
        }
        return false;
    }


    public boolean confirmMeeting(String attendee){
        int i = attendees.indexOf(attendee);
        if(!confirmed.get(i)){
            confirmed.set(i, true);
            return true;
        }
        return false;
    }
}
