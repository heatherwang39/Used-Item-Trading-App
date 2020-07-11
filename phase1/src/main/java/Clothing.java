package main.java;

import java.util.HashMap;

public class Clothing extends Item {

    private String brand;

    public Clothing(String name, String description, int id, String brand) {
        super(name, description, id);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString(){
        // return "main.java.Book name: " + name +", description: " + description + ", brand: " + brand ;
        return "Clothing name: " + getName() +", description: " + getDescription() + ", brand: " + getBrand() ;
    }

    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a HashMap
     */
    public HashMap<String, String> getData(){return new HashMap<>();}

}