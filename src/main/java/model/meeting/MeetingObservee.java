package main.java.model.meeting;

/**
 * An interface for something in the meeting Package that updates its observers
 * when certain changes happen.
 *
 * @author Warren Zhu
 * @version %I%, %G%
 * @since Phase 2
 */

public interface MeetingObservee {


    /** Add an observer to this subject/observed object
     *
     * @param meetingObserver The newly-added observer for this object
     */
    void attachMeetingObserver(MeetingObserver meetingObserver);


    /** Remove an observer from this subject/observed object
     *
     * @param meetingObserver The recently-removed observer from this object
     */
    void detachMeetingObserver(MeetingObserver meetingObserver);


    /** Notify its observer that a meeting has been accepted
     *
     * @param meetingID The meeting that was accepted
     */
    void notifyAccepted(int meetingID);


    /** Notify its observers that a meeting has been confirmed
     *
     * @param meetingID The meeting that was confirmed
     */
    void notifyConfirmed(int meetingID);


    /** Record the fact that the given meeting has been cancelled
     *
     * @param meetingID The ID of the cancelled meeting
     */
    void notifyCancelled(int meetingID);
}
