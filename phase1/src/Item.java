abstract class Item{

    private String name;
    private String type;
    private Integer id;
    private Boolean verified;
    //TODO: determine what types of items we have. (e.g. clothes, books,...)

    public Item(String name, String type){
        this.name = name;
        this.type = type;
    }
}