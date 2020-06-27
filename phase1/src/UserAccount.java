import java.util.ArrayList;
import java.util.List;

public class UserAccount extends Account {

    private String username;
    private String password;
    private String email;
    private boolean isFrozen;
    private List<Item> inventory;
    private List<Item> wishlist;

    public UserAccount(String username, String password, String email){
        super(username, password, email);

        isFrozen = false;
    }

    public void setFrozen(){isFrozen = !isFrozen;}

    public List<Item> getInventory(){return inventory;} //changed typed from String to List<Item>

    public List<Item> getWishlist(){return wishlist;}

        //isFrozen = false;     ???? TODO: I commented this out because idk why it's here??
    //}

    public boolean isAdmin(){
        return false;
    }

}
