public class UserAccount extends Account {

    private String username;
    private String password;
    private String email;
    private boolean isFrozen;
    private List<Item> inventory;
    private List<Item> wishlist;

    public UserAccount(String username, String password, String email){
        super(username, password, email);

        isAdmin = false;
        isFrozen = false;
    }

    public void setFrozen(){isFrozen = !isFrozen;}

    public String getInventory(){return inventory}

    public String getWishlist(){return wishlist}

        isFrozen = false;
    }

    public boolean isAdmin(){
        return false;
    }




}
