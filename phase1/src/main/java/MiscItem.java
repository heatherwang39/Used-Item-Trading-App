package main.java;

/**
 * Entity class representing a unique tradable Item
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class MiscItem extends Item{
    /**
     * Creates an Item with the name and description of Item
     *
     * @param name name of item
     * @param description item description
     * @param id item id
     */
    public MiscItem(String name, String description, int id) {
        super(name, description, id);
    }
}
