package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AccountManager {

    HashMap<String, Account> accounts;



    public AccountManager() throws IOException {
        accounts = readAccounts();
    }

    HashMap<String, Account> readAccounts() throws IOException {
        accounts = new HashMap<>();
        // Begin citation: https://stackoverflow.com/questions/5464631/java-read-a-file-if-it-doesnt-exist-create-it
        File accountsCSV = new File("main/resources", "accounts.csv");
        if (!accountsCSV.isFile() && !accountsCSV.createNewFile())
        {
            throw new IOException("Error creating new file: " + accountsCSV.getAbsolutePath());
        }
        // End citation

        // Begin citation: https://www.baeldung.com/java-csv-file-array
        BufferedReader r = new BufferedReader(new FileReader(accountsCSV));
        String line;
        while ((line = r.readLine()) != null) {
            String[] values = line.split(",");
            // End citation
            switch (values[0]) {
                case "user":
                    accounts.put(values[1], new UserAccount(values[1], values[2], values[3],
                            Boolean.parseBoolean(values[4])));

                case "admin":
                    accounts.put(values[1], new AdminAccount(values[1], values[2], values[3]));
            }
        }
        r.close();
        // End citation
        return accounts;
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

    public void getAccount (String username){
    }

    public boolean tryLogin(String username, String password){
        return accounts.containsKey(username) && accounts.get(username).isPassword(password);
    }

    public static void main(String[] args) {
        // AccountManager a = new AccountManager();
    }


}
