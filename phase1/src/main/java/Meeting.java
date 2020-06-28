package main.java;

import java.time.LocalDateTime;
import java.util.List;

public interface Meeting {

    String getPlace();

    LocalDateTime getTime();

    List<Account> getAttendees();
}
