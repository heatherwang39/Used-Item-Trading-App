public class Book extends Item{

    private String author;

    public Book(String name, String description, String author) {
        super(name,description);
        this.author = author;
    }

    @Override
    public String toString(){
        return "Book name: " + name +", description: " + description + ", author: " + author ;
    }
}