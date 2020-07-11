package main.java;

import java.util.List;

/**
 * Distinguishes between Entities (singular units of associated data), and non Entities (aggregators of data, lists of
 * Entities)
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public interface Entity {

    /**
     * Returns a HashMap of all displayable and obtainable information from the Entity
     * @return The map's key is the attribute name and the value is the attribute data
     */
    List<List<String>> getData();
}
