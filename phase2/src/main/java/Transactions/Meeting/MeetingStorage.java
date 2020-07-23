package main.java.Transactions.Meeting;

import java.util.List;

public class MeetingStorage {
    private List<Meeting> meetings;

    public MeetingStorage(List<Meeting> meetings){
        this.meetings = meetings;
    }


    private Meeting getMeeting(int meetingNumber) throws MeetingNumberException {
        for (Meeting m : meetings) {
            if (m.getMeetingNumber() == meetingNumber) {
                return m;
            }
        }
        throw new MeetingNumberException();
    }

    //TODO: Finish this class

}
