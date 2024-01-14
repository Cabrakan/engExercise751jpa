package ch.engenius.bank;

import java.math.BigDecimal;

public class Account {
    private BigDecimal money;

    public Account(BigDecimal money) {
        this.money = money;
    }

    public void withdraw(BigDecimal amount) {
        if (money.subtract(amount).signum() == -1) {
            throw new IllegalStateException("Not enough credits on account.");
        }

        this.money = this.money.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        this.money = this.money.add(amount);
    }

    public BigDecimal getMoney() {
        return money;
    }
}
