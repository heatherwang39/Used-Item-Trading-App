package main.java.systems;

import java.util.HashMap;
import java.util.List;

public interface UserInterface {

    List<HashMap<String, String>> viewInventory(String username);

    List<HashMap<String, String>> viewWishlist(String username);
}
