public class Clothes extends Item{

    private String brand;

    public Clothes(String name, String description, String brand) {
        super(name,description);
        this.brand = brand;
    }

    @Override
    public String toString(){
        return "Book name: " + name +", description: " + description + ", brand: " + brand ;
    }
}