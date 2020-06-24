import java.util.HashMap;
import java.util.Optional;

public class AccountManager {

    HashMap<String, Account> accounts;

    public AccountManager() {
        accounts = new HashMap<>();
        // TODO: csv parser for accounts
    }

    public UserAccount createUserAccount(String username, String password, String email) {
        UserAccount user = new UserAccount(username, password, email);
        // TODO: save to csv
        accounts.put(username, user);
        return user;
    }

    public AdminAccount createAdminAccount(String username, String password, String email){
        AdminAccount user = new AdminAccount(username, password, email);
        // TODO: save to csv
        accounts.put(username, user);
        return user;
    }

    public Account getAccount (String username){

    }

    public boolean tryLogin(String username, String password){
        return accounts.containsKey(username) && accounts.get(username).isPassword(password);
    }



}
