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
                return new AwayStatus(statusType);
            case "FROZEN":
                return new FrozenStatus(statusType);
            case "GILDED":
                return new GildedStatus(statusType);
            case "MUTED":
                return new MutedStatus(statusType);
            case "NEW":
                return new NewStatus(statusType);
            default:
                return null;
        }
    }
}
