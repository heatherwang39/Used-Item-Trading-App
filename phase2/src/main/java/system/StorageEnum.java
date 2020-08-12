package main.java.system;

/**
 * Enum Class for Storage types
 *
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 2
 */
public enum StorageEnum{
    ACCOUNT("ACCOUNT"), MEETING("MEETING"), MESSAGE("MESSAGE"),
    TRADE("TRADE"), ITEM("ITEM");

    private final String name;

    /**
     * Class constructor.
     *
     * @param name Enum name
     */
    StorageEnum(String name) {
        this.name = name;
    }

    /**
     * Get string representation
     *
     * @return String
     */
    public String toString() {
        return name;
    }
}