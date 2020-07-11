package main.java;

import java.util.HashMap;

public class Book extends Item {

    private String author;

    public Book(String name, String description, int id, String author) {
        super(name, description, id);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString(){
        return "Book name: " + getName() +", description: " + getDescription() + ", author: " + getAuthor() ;
    }

    /**
     * Method required by implementations of Entity
     * @return this class's attributes name and value in String format in a HashMap
     */
    public HashMap<String, String> getData(){return new HashMap<>();}
}