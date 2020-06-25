public class Book extends Item{

    private String author;

    public Book(String name, String description, int price, String author) {
        super(name,description,price);
        this.author = author;
    }

    @Override
    public String toString(){
        return "Book name: " + name +", description: " + description + ", price: " + price +", author: " + author ;
    }
}