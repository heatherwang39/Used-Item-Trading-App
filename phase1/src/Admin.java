class Admin extends User{

    private String userName;
    private String pw;
    private String email;
    private boolean isAdmin;

    public Admin(String userName, String pw, String email){
        super(userName, pw, email);
        isAdmin = true;
    }
}