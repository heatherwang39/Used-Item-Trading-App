package main.java;

import java.util.List;

/**
 * An object that takes in entity classes and constructs a string to be printed representing a list of entities.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class EntityDisplay {

    private final StringBuilder string = new StringBuilder();
    private int count = 1;

    /**
     * Class Constructor
     */
    public EntityDisplay(String title){
        string.append(title).append(":\n\n");
    }

    /**
     * Records the Entity's data for display
     * @param entity input Entity
     */
    public void insert(Entity entity){
        string.append("( ").append(count).append(" )");
        for (List<String> lst: entity.getData()) {
            string.append(lst.get(0)).append(": ").append(lst.get(1)).append("\n");
        }
        string.append("\n");
        count += 1;
    }

    /**
     * Prints out inserted Entities' data in a readable format
     */
    public void display(){
        System.out.println(string);
    }
}
