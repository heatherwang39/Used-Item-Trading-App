import java.util.List;
import java.util.Optional;

public interface TwoMeetings {

    Optional<List> getFirstMeeting();

    boolean setFirstMeeting();

    Optional<List> getSecondMeeting();

    boolean setSecondMeeting();
}