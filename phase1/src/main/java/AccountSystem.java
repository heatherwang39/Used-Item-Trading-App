package main.java;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AccountSystem {

    private final AccountManager am;

    public AccountSystem(String accountsPath) throws IOException, ClassNotFoundException {
        am = new AccountManager(accountsPath);
        try {
            am.createAdminAccount("admin", "admin", "admin@trader.org");
            am.createUserAccount("Sarah.alk", "123456", "sarah@trader.org", false);
        } catch (UsernameInUseException | EmailInUseException | InvalidEmailException | InvalidLoginException e){
            //it's okay if this is not the first time the code is getting run
        }
    }

    /**
     * returns a User Account associated with the input info of the user after signing in to an existing
     * @param input the input options:
     *              1 represents option to Sign In to an existing account
     *              2 represents option to Register a new account
     * @return  returns the logged in User Account
     * @throws AccountNotFoundException
     * @throws InvalidOptionException
     * @throws IOException
     */
    public Account login(String input) throws AccountNotFoundException, IOException, InvalidOptionException {
        if (input.equals("1")){
            return signIn(am);
        }
        if (input.equals("2")) {
            return register(am);
        }else{
            throw new InvalidOptionException();
        }
    }

    private Account signIn(AccountManager am) throws IOException {
        try (BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Username: ");
            String username = keyboard.readLine();
            System.out.println("Password: ");
            String pw = keyboard.readLine();

            if (am.tryLogin(username, pw)){
                return am.getAccount(username);
            } else { throw new AccountNotFoundException(); }
        } catch (AccountNotFoundException e){
            System.out.println("Invalid Username or Password. Please try again.");
            //return signIn(am);
        }
        return signIn(am);

    }

    private Account register(AccountManager am) throws IOException, AccountNotFoundException { //this doesn't work properly yet need to fix some stuff

        System.out.println("Enter email: "); //TODO make this method have less duplicate lines for errors
        String email = System.in.toString();

        System.out.println("Enter Username: ");
        String username = System.in.toString();
        System.out.println("Enter Password: ");
        String pw = System.in.toString();

        try {
            if (!am.isUsernameInUse(username)) {
                am.createUserAccount(username, pw, email, false);
            } else {
                throw new UsernameInUseException();
            }

        } catch (UsernameInUseException e) {
            e.printStackTrace(); // TODO: check if I can just override .toString in the exception, instead of  writing the following print lines
            System.out.println("This Username is already in use. Please choose another Username.");
            System.out.println("Enter Username: ");
            username = System.in.toString();
        } catch (EmailInUseException e){
            System.out.println("This email address is already in use. Please try again.");
        } catch (InvalidEmailException e){
            System.out.println("Invalid email. Please try again.");
        } catch (InvalidLoginException e){
            System.out.println("Invalid Username or Password format. Please try again with alphanumerics and special characters only.");
        }
        return am.getAccount(username);
    }


}
