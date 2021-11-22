package main.java.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Presenter for formatting Meeting data into an easy to output GUI format
 * Useful for Meeting tab
 *
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */
public class MeetingPresenter {
    private final String username;

    /**
     * Initializes a new MeetingPresenter
     */
    public MeetingPresenter(String username) {
        this.username = username;
    }


    /**
     * Formats a list of hashmaps representing data of meetings for GUI Lists to display
     * Format of meeting: "id. otherAttendee1, otherAttendee2, ..., Place: place (dow mon dd hh:mm:ss zzz yyy)"
     * Example: "5. Fadi, Place: Robarts Library (Mon Aug 10 10:30:00 2020)"
     *
     * @param meetings List of hashmaps that contain meeting data
     * @return List of formatted lists of Strings of each meeting data
     */
    public List<String> formatMeetingToListView(List<HashMap<String, List<String>>> meetings) {
        List<String> formatMeeting = new ArrayList<>();
        for (HashMap<String, List<String>> meetingData : meetings) {
            List<String> otherAttendees = meetingData.get("attendees");
            otherAttendees.remove(username);
            StringBuilder meetingInfo = new StringBuilder(meetingData.get("id").get(0));
            meetingInfo.append(". ");
            for (String attendee: otherAttendees) {
                meetingInfo.append(attendee);
                meetingInfo.append(", ");
            }
            meetingInfo.append("Place: ");
            meetingInfo.append(meetingData.get("place").get(0));
            meetingInfo.append(" (");
            meetingInfo.append(meetingData.get("datetime").get(0));
            meetingInfo.append(")");
            formatMeeting.add(meetingInfo.toString());
        }
        return formatMeeting;
    }
}
