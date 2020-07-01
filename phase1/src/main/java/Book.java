package main.java;

import java.io.Serializable;

public class Book extends Item implements Serializable {

    private String author;

    public Book(String name, String description, String author) {
        super(name,description);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString(){
        return "Book name: " + getName() +", description: " + getDescription() + ", author: " + getAuthor() ;
    }
}