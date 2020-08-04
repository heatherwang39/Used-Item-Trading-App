package main.java.model.status;

/**
 * StatusFactory is a wrapper class for a factory method that returns a new Status.
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public class StatusFactory {
    /**
     * Factory method that returns a new Status.
     * @param statusType Status stype
     * @return Status
     */
    public Status getStatus(String statusType) {
        switch (statusType.toUpperCase()) {
            case "AWAY":
                return new AwayStatus();
            case "FROZEN":
                return new FrozenStatus();
            case "GILDED":
                return new GildedStatus();
            case "MUTED":
                return new MutedStatus();
            case "NEW":
                return new NewStatus();
            default:
                return null;
        }
    }
}
