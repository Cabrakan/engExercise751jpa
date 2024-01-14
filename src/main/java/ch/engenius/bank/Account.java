package ch.engenius.bank;

import java.math.BigDecimal;

public class Account {
    private BigDecimal money;

    public Account(BigDecimal money) {
        this.money = money;
    }

    public void withdraw(BigDecimal amount) {
        updateAccountBalance(amount, AccountAction.WITHDRAW);
    }

    public void deposit(BigDecimal amount) {
        updateAccountBalance(amount, AccountAction.DEPOSIT);
    }

    private synchronized void updateAccountBalance(BigDecimal amount, AccountAction accountAction) {
        if (AccountAction.WITHDRAW.equals(accountAction)) {
            if (money.subtract(amount).signum() == -1) {
                throw new IllegalStateException("Not enough credits on account.");
            }

            money = money.subtract(amount);
        }

        if (AccountAction.DEPOSIT.equals(accountAction)) {
            if (amount.signum() == -1) {
                throw new IllegalStateException("Amount cannot be negative.");
            }

            money = money.add(amount);
        }
    }

    public BigDecimal getMoney() {
        return money;
    }
}
