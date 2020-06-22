import java.util.ArrayList;
import java.util.List;

interface Trade {

    Integer confirmed();
    List<Item> getItems();
    List<Account> getAccounts();
    Boolean changeStatus();

}
