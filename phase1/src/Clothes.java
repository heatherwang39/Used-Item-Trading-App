public class Clothes extends Item{

    private String brand;

    public Clothes(String name, String description, int price, String brand) {
        super(name,description,price);
        this.brand = brand;
    }

    @Override
    public String toString(){
        return "Book name: " + name +", description: " + description + ", price: " + price +", brand: " + brand ;
    }
}