package main.java;

public class Book extends Item{

    private String author;

    public Book(String name, String description, String author) {
        super(name,description);
        this.author = author;
    }

    @Override
    public String toString(){
        // TODO: change name to getName and decription to getDescription
        // return "main.java.Book name: " + name +", description: " + description + ", author: " + author ;
        return "";
    }
}