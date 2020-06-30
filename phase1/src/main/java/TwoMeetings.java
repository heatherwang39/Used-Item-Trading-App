package main.java;

import java.time.LocalDateTime;
import java.util.List;

public interface TwoMeetings {



    String getFirstMeetingPlace() throws NoMeetingException;


    LocalDateTime getFirstMeetingTime() throws NoMeetingException;


    boolean getFirstMeetingAccepted();


    boolean getFirstMeetingConfirmed();


    boolean setFirstMeeting(String place, LocalDateTime time) throws TimeException;


    boolean suggestFirstMeeting(String place, LocalDateTime time, String suggester) throws
            WrongAccountException, TimeException;


    boolean acceptFirstMeeting(String acceptor) throws NoMeetingException, WrongAccountException;


    boolean confirmFirstMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException;






    String getSecondMeetingPlace() throws NoMeetingException;


    LocalDateTime getSecondMeetingTime() throws NoMeetingException;


    boolean getSecondMeetingAccepted();


    boolean getSecondMeetingConfirmed();


    boolean setSecondMeeting(String place, LocalDateTime time) throws TimeException;


    boolean suggestSecondMeeting(String place, LocalDateTime time, String suggester) throws
            WrongAccountException, TimeException;


    boolean acceptSecondMeeting(String acceptor) throws NoMeetingException, WrongAccountException;


    boolean confirmSecondMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException;
}