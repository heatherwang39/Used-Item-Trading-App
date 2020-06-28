import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class AccountManager {

    HashMap<String, Account> accounts;



    public AccountManager() throws IOException {
        accounts = new HashMap<>();
        // Begin citation: https://stackoverflow.com/questions/5464631/java-read-a-file-if-it-doesnt-exist-create-it
        File accountsCSV = new File("main/resources", "accounts.csv");
        if (!accountsCSV.isFile() && !accountsCSV.createNewFile())
        {
            throw new IOException("Error creating new file: " + accountsCSV.getAbsolutePath());
        }
        // End citation
        Scanner scanner = new Scanner(accountsCSV);
        try {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
        }
            }
        }
    }

    public UserAccount createUserAccount(String username, String password, String email, boolean isFrozen) {
        UserAccount user = new UserAccount(username, password, email, isFrozen);
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
