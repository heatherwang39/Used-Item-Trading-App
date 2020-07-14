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

    /**
     * Creates a Book item with the name and description of Clothing
     */
    public Book(String name, String description, int id, String author) {
        super(name, description, id);
        this.author = author;
    }

    /**
     * Get author of this Book
     *
     * @return name of the item
     */
    public String getAuthor() {
        return author;
    }

    /**
     * The toString() method for Book instances
     *
     * @return stylized print message containing information about this book.
     */
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