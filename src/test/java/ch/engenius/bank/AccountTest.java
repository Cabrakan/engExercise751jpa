package ch.engenius.bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AccountTest {
    private Account account;

    @Before
    public void setUpAccount() {
        account = new Account(BigDecimal.ZERO);
    }

    @Test
    public void testDeposit() {
        account.deposit(BigDecimal.valueOf(100));

        assertThat(account.getMoney())
                .isEqualTo(BigDecimal.valueOf(100));
    }


    @Test
    public void testDeposit_AmountIsNegative() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> account.deposit(BigDecimal.valueOf(-100)))
                .withMessage("Amount cannot be negative.");
    }

    @Test
    public void testWithdraw() {
        account = new Account(BigDecimal.valueOf(500));
        account.withdraw(BigDecimal.valueOf(200));

        assertThat(account.getMoney())
                .isEqualTo(BigDecimal.valueOf(300));
    }

    @Test
    public void testWithdraw_NotEnoughFunds() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> account.withdraw(BigDecimal.valueOf(100)))
                .withMessage("Not enough credits on account.");
    }
}