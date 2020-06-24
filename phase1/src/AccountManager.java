import java.util.HashMap;

public class AccountManager {

    HashMap<String, Account> users;

    public AccountManager() {
        users = new HashMap<>();
        // TODO: csv parser for accounts
    }

    public UserAccount createUserAccount(String username, String password, String email) {
        UserAccount user = new UserAccount(username, password, email);
        // TODO: save to csv
        users.put(username, user);
        return user;
    }

    public UserAccount getUser (String username) {

    }

    public boolean tryLogin(String username, String password) {
        return users.containsKey(username) && users.get(username).isPassword(password);
    }



}
