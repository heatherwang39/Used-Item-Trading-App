package main.java;

public class Clothes extends Item{

    private String brand;

    public Clothes(String name, String description, String brand) {
        super(name,description);
        this.brand = brand;
    }

    @Override
    public String toString(){
        // TODO: change name to getName and decription to getDescription
        // return "main.java.Book name: " + name +", description: " + description + ", brand: " + brand ;
        return "";
    }
}