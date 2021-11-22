package main.java.model.meeting;

/**
 * An interface for something that "observes" something in the meeting package
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */
public interface MeetingObserver {

    /** Record the fact that the given meeting has been accepted
     *
     * @param meetingID The ID of the accepted meeting
     */
    void updateAccepted(int meetingID);


    /** Record the fact that the given meeting has been confirmed
     *
     * @param meetingID The ID of the confirmed meeting
     */
    void updateConfirmed(int meetingID);


    /** Record the fact that the given meeting has been cancelled
     *
     * @param meetingID The ID of the cancelled meeting
     */
    void updateCancelled(int meetingID);
}
