/**
 * Represents an user account in the Trade system. Users have basic privileges, and can make offers so long as their
 * account isn't frozen.
 * @author Robbert Liu
 * @version %I%, %G%
 * @since Phase 1
 */
public class UserAccount extends Account {

    private boolean isFrozen;

    /**
     * Class constructor
     * @param username account username
     * @param password account password
     * @param email account email address
     */
    public UserAccount(String username, String password, String email){
        super(username, password, email);
        isFrozen = false;
    }

    /**
     * Set account status to frozen
     */
    public void freeze() { isFrozen = true; }

    /**
     * Set account status to not frozen
     */
    public void unfreeze() { isFrozen = false; }

    /**
     * Return false because users do not have admin privileges
     */
    public boolean isAdmin(){
        return false;
    }




}
