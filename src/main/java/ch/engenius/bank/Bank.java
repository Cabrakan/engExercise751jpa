package ch.engenius.bank;

import java.math.BigDecimal;
import java.util.HashMap;

public class Bank {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public void registerAccount(int accountID, BigDecimal amount) {
        Account account = new Account(amount);
        accounts.put(accountID, account);
    }

    public Account getAccount(int accountID) {
        return accounts.get(accountID);
    }
}
