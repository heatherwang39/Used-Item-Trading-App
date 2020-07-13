package main.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A Book class in the main.java.Trade system representing a concrete item object of the program.
 * @author Heather Wang
 * @version %I%, %G%
 * @since Phase 1
 */

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
    public List<List<String>> getData(){
        List<List<String>> data = super.getData();
        data.add(Arrays.asList("Author", author));
        return data;
    }
}