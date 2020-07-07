package main.java;

import java.io.Serializable;

public class Clothes extends Item implements Serializable {

    private String brand;

    public Clothes(String name, String description, int id, String brand) {
        super(name, description, id);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString(){
        // return "main.java.Book name: " + name +", description: " + description + ", brand: " + brand ;
        return "Clothes name: " + getName() +", description: " + getDescription() + ", brand: " + getBrand() ;
    }
}