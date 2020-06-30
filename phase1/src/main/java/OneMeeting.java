package main.java;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OneMeeting {


    String getMeetingPlace() throws NoMeetingException;


    LocalDateTime getMeetingTime() throws NoMeetingException;


    boolean getMeetingAccepted();


    boolean getMeetingConfirmed();


    boolean setMeeting(String place, LocalDateTime time) throws TimeException;


    boolean suggestMeeting(String place, LocalDateTime time, String suggester) throws
            WrongAccountException, TimeException;


    boolean acceptMeeting(String acceptor) throws NoMeetingException, WrongAccountException;


    boolean confirmMeeting(String attendee) throws NoMeetingException, WrongAccountException, TimeException;
}