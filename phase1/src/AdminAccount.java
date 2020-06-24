class AdminAccount extends Account {

    private String username;
    private String password;
    private String email;

    public AdminAccount(String username, String password, String email){
        super(username, password, email);
    }

    public boolean isAdmin(){
        return true;
    }

}