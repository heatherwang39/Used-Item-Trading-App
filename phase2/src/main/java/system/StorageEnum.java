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
    TRADE("TRADE"), ITEM("ITEM"), STATUS("STATUS");

    private final String name;

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