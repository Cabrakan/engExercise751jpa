package ch.engenius.bank;

import java.math.BigDecimal;

public class Account {
    private BigDecimal money;

    public Account(BigDecimal money) {
        this.money = money;
    }

    public synchronized void withdraw(BigDecimal amount) {
        if (money.subtract(amount).signum() == -1) {
            throw new IllegalStateException("Not enough credits on account.");
        }

        money = money.subtract(amount);
    }

    public synchronized void deposit(BigDecimal amount) {
        if (amount.signum() == -1) {
            throw new IllegalStateException("Amount cannot be negative.");
        }

        money = money.add(amount);
    }

    public BigDecimal getMoney() {
        return money;
    }
}
