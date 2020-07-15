package main.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A Clothing class in the main.java.Trade system representing a concrete item object of the program.
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

public class Clothing extends Item {

    private final String brand;

    /**
     * Creates an Clothing item with the name and description of Clothing
     */
    public Clothing(String name, String description, int id, String brand) {
        super(name, description, id);
        this.brand = brand;
    }

    /**
     * Get brand of the item
     *
     * @return brand of the item
     */
    public String getBrand() {
        return brand;
    }

    /**
     * The toString() method for Clothing instances
     *
     * @return the stylized message which is printed when this method is called
     */
    @Override
    public String toString(){
        // return "main.java.Book name: " + name +", description: " + description + ", brand: " + brand ;
        return "Clothing name: " + getName() +", description: " + getDescription() + ", brand: " + getBrand() ;
    }

    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a HashMap
     */
    public List<List<String>> getData(){
        List<List<String>> data = super.getData();
        data.add(Arrays.asList("Brand", brand));
        return data;
    }

}