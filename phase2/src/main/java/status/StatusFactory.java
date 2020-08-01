package main.java.status;

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
     * @param id Status id
     * @param statusType Status stype
     * @return Status
     */
    public Status getStatus(int id, String statusType) {
        switch (statusType.toUpperCase()) {
            case "AWAY":
                return new AwayStatus(id, statusType);
            case "FROZEN":
                return new FrozenStatus(id, statusType);
            case "GILDED":
                return new GildedStatus(id, statusType);
            case "MUTED":
                return new MutedStatus(id, statusType);
            case "NEW":
                return new NewStatus(id, statusType);
            default:
                return null;
        }
    }
}
