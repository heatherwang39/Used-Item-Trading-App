public class UserAccount extends Account {

    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private boolean isFrozen;

    public UserAccount(String username, String password, String email) {
        super(username, password, email);
        isAdmin = true;
        isFrozen = false;
    }
}
