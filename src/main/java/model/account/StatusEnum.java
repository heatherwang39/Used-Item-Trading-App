package main.java.model.account;
/**
 * Enum Class for Storage types
 *
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 2
 */

public enum  StatusEnum {
    NEW("NEW"), AWAY("AWAY"), MUTED("MUTED"),FROZEN("FROZEN"),GILDED("GILDED");
    private final String name;
    /**
     * Class constructor
     *
     * @param name the name of the status enum
     */
    StatusEnum(String name){this.name = name;}

    /**
     * Get string representation
     *
     * @return String
     */
    public String toString() {
        return name;
    }
}
