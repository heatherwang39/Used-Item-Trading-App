package main.java.controllers;

import main.java.model.account.AccountStorage;

import java.util.List;

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
