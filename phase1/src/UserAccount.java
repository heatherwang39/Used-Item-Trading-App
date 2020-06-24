public class UserAccount extends Account {

    private String username;
    private String password;
    private String email;
    private boolean isFrozen;

    public UserAccount(String username, String password, String email){
        super(username, password, email);
        isFrozen = false;
    }

    public boolean isAdmin(){
        return false;
    }



}
