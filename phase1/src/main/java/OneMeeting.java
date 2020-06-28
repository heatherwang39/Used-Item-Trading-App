package main.java;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OneMeeting {

    TwoPersonMeeting getMeeting();

    boolean setMeeting(TwoPersonMeeting meeting) throws WrongAccountException, TimeException;
}