package main.java.controllers;

public class AdminController implements AccountController {



    /** Return the type of account that is linked with this controller
     *
     * @return The account type linked with this controller
     */
    public String getAccountType(){
        return "ADMIN";
    }
}