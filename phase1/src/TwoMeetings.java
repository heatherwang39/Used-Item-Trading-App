import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TwoMeetings {

    Optional<List> getFirstMeeting();

    boolean setFirstMeeting(String place, LocalDateTime time);

    boolean confirmFirstMeeting(UserAccount trader);

    boolean acceptFirstMeeting(UserAccount trader);

    Optional<List> getSecondMeeting();

    boolean setSecondMeeting(String place, LocalDateTime time);

    boolean confirmSecondMeeting(UserAccount trader);

    boolean acceptSecondMeeting(UserAccount trader);
}