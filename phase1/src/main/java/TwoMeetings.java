package main.java;

import java.time.LocalDateTime;
import java.util.List;

public interface TwoMeetings {

    TwoPersonMeeting getFirstMeeting();

    boolean setFirstMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException;

    TwoPersonMeeting getSecondMeeting();

    boolean setSecondMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException;
}