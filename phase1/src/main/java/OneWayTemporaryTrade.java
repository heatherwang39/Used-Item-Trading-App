package main.java;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OneWayTemporaryTrade extends OneWayTrade implements TwoMeetings{
    private List firstMeeting;
    private List firstMeetingAccepted;
    private List firstMeetingConfirmed;
    private List secondMeeting;
    private List secondMeetingAccepted;
    private List secondMeetingConfirmed;
    private int warnings;
    private int max_warnings = 6;


    public OneWayTemporaryTrade(int tradeNumber, UserAccount sender, UserAccount receiver, Item item){

        super(tradeNumber, sender, receiver, item);

        firstMeeting = new ArrayList();

        firstMeetingAccepted = new ArrayList();
        firstMeetingAccepted.add(false);
        firstMeetingAccepted.add(false);

        firstMeetingConfirmed = new ArrayList();
        firstMeetingConfirmed.add(false);
        firstMeetingConfirmed.add(false);

        secondMeeting = new ArrayList();

        secondMeetingAccepted = new ArrayList();
        secondMeetingAccepted.add(false);
        secondMeetingAccepted.add(false);

        secondMeetingConfirmed = new ArrayList();
        secondMeetingConfirmed.add(false);
        secondMeetingConfirmed.add(false);
    }


    public Optional<List> getFirstMeeting(){
        if(firstMeeting.isEmpty()){
            return null;
        }
        List meetingCopy = new ArrayList(firstMeeting);
        return meetingCopy;
    }


    public Optional<List> getSecondMeeting(){
        if(secondMeeting.isEmpty()){
            return null;
        }
        List meetingCopy = new ArrayList(secondMeeting);
        return meetingCopy;
    }


    public boolean setFirstMeeting(String place, LocalDateTime time){
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        firstMeeting = new ArrayList();
        firstMeeting.add(place);
        firstMeeting.add(time);
        return true;
    }


    public boolean setSecondMeeting(String place, LocalDateTime time){
        warnings += 1;
        if(warnings > max_warnings){
            setStatus(-1);
            return false;
        }
        secondMeeting = new ArrayList();
        secondMeeting.add(place);
        secondMeeting.add(time);
        secondMeeting.add(false);
        secondMeeting.add(false);
        return true;
    }










    //TODO: May change this function to raise errors
    public boolean acceptSecondMeeting(UserAccount trader){
        if(secondMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = acceptSecondMeetingSender();
            if(getSecondAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = acceptSecondMeetingReceiver();
            if(getSecondAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean acceptSecondMeetingSender(){
        if(secondMeetingAccepted[0]){
            return false;
        }
        secondMeetingAccepted[0] = true;
        return true;
    }

    private boolean acceptSecondMeetingReceiver(){
        if(secondMeetingAccepted[1]){
            return false;
        }
        secondMeetingAccepted[1] = true;
        return true;
    }

    public boolean getSecondAccepted(){
        if(secondMeeting.isEmpty()){
            return false;
        }
        return secondMeetingAccepted[0] && secondMeetingAccepted[1];
    }









    //TODO: May change this function to raise errors
    private boolean confirmFirstMeeting(UserAccount trader){
        if(firstMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmFirstMeetingSender();
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmFirstMeetingReceiver();
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmFirstMeetingSender(){
        if(firstMeetingConfirmed[0]){
            return false;
        }
        firstMeetingConfirmed[0] = true;
        return true;
    }

    private boolean confirmFirstMeetingReceiver(){
        if(firstMeetingConfirmed[1]){
            return false;
        }
        firstMeetingConfirmed[1] = true;
        return true;
    }

    public boolean getFirstConfirmed(){
        return firstMeeting[0] && firstMeeting[1];
    }









    //TODO: May change this function to raise errors
    public boolean acceptFirstMeeting(UserAccount trader){
        if(firstMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = acceptFirstMeetingSender();
            if(getFirstAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = acceptFirstMeetingReceiver();
            if(getFirstAccepted()){
                warnings = 0;
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean acceptFirstMeetingSender(){
        if(firstMeetingAccepted[0]){
            return false;
        }
        firstMeetingAccepted[0] = true;
        return true;
    }

    private boolean acceptFirstMeetingReceiver(){
        if(firstMeetingAccepted[1]){
            return false;
        }
        firstMeetingAccepted[1] = true;
        return true;
    }

    public boolean getFirstAccepted(){
        if(firstMeeting.isEmpty()){
            return false;
        }
        return firstMeetingAccepted[0] && firstMeetingAccepted[1];
    }








    //TODO: May change this function to raise errors
    private boolean confirmSecondMeeting(UserAccount trader){
        if(secondMeeting.isEmpty()){
            return false;
            //TODO: Raise no meeting error?
        }
        if(getSender().equals(trader)){
            boolean confirmation = confirmSecondMeetingSender();
            if (getSecondConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        if(getReceiver().equals(trader)){
            boolean confirmation = confirmSecondMeetingReceiver();
            if (getSecondConfirmed()){
                setStatus(2);
            }
            return confirmation;
        }
        else{
            return false;
            //TODO: Raise wrong user error?
        }
    }

    private boolean confirmSecondMeetingSender(){
        if(secondMeetingConfirmed[0]){
            return false;
        }
        secondMeetingConfirmed[0] = true;
        return true;
    }

    private boolean confirmSecondMeetingReceiver(){
        if(secondMeetingConfirmed[1]){
            return false;
        }
        secondMeetingConfirmed[1] = true;
        return true;
    }

    public boolean getSecondConfirmed(){
        if(secondMeeting.isEmpty()){
            return false;
        }
        return secondMeetingConfirmed[0] && secondMeetingConfirmed[1];
    }

}
