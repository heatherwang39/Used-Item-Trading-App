class AdminAccount extends Account {

    private String username;
    private String password;
    private String email;
    private boolean isAdmin;

    public AdminAccount(String username, String password, String email){
        super(username, password, email);
        isAdmin = true;
    }

    public boolean isAdmin(){
        return true;
    }
}