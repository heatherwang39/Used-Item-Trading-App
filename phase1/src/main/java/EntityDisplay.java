package main.java;

import java.util.List;

/**
 * An object that takes in entity classes and constructs a string to be printed representing a list of entities.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class EntityDisplay {
    private final String title;
    private final StringBuilder body = new StringBuilder();
    private int count = 1;

    /**
     * Class Constructor
     */
    public EntityDisplay(String title){
        this.title = title + ":\n\n";
    }

    /**
     * Records the Entity's data for display
     * @param entity input Entity
     */
    public void insert(Entity entity){
        body.append("( ").append(count).append(" )");
        for (List<String> lst: entity.getData()) {
            body.append(lst.get(0)).append(": ").append(lst.get(1)).append("\n");
        }
        body.append("\n");
        count += 1;
    }

    /**
     * Prints out inserted Entities' data in a readable format
     */
    public void display(){

        if (count > 1){
            System.out.println(title + body);
        } else {
            System.out.println(title + "(Empty)");
        }
    }
}
