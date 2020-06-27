import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OneMeeting {

    Optional<List> getMeeting();

    boolean setMeeting(String place, LocalDateTime time);

    boolean confirmMeeting(UserAccount trader);

    boolean acceptMeeting(UserAccount trader);
}