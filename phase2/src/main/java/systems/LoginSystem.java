package main.java.systems;

import main.java.account.*;
import main.java.item.ItemStorage;
import main.java.transactions.trade.*;

import java.util.*;

/**
 * Presenter that returns the needed information for GUI client to display for an account that is able to login
 * @author Fadi Hareth
 * @version %I%, %G%
 * @since Phase 2
 */

class LoginSystem extends GuestSystem implements AccountInterface, Loginable {

    protected TradeStorage ts;
    protected AccountStorage as;
    private String username;

    public LoginSystem(ItemStorage is, TradeStorage ts, AccountStorage as, String username) {
        super(is);
        this.ts = ts;
        this.as = as;
        this.username = username;
    }

    @Override
    public void logout(){ //Should this even be handled here?
    }

    @Override
    public List<String> viewAccountInformation(){
        return new ArrayList<>(); //TODO: implement
    }

    @Override
    public boolean signIn(String password) {return as.tryLogin(username, password);} //TODO: reconsider how sign in works
}