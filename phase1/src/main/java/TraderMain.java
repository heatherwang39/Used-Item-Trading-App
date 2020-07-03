package main.java;

import java.io.IOException;

public class TraderMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException,
            InvalidEmailException, InvalidLoginException, AccountNotFoundException, InvalidOptionException {

        TradeSystem em = new TradeSystem();
        em.run();

    }
}