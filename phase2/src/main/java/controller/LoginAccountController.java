package main.java.controller;

import main.java.model.account.AccountStorage;

public abstract class LoginAccountController implements AccountController {
    private String username;
    private AccountStorage accountStorage;

    public LoginAccountController(String username, AccountStorage accountStorage){
        this.username = username;
        this.accountStorage = accountStorage;
    }

    public String getUsername(){
        return username;
    }
}
