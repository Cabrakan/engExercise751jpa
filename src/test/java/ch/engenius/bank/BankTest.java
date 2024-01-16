package ch.engenius.bank;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class BankTest {
    private Bank bank;

    @Before
    public void setUp() {
        bank = new Bank();
    }

    @Test
    public void testRegisterAndGetAccount() {
        int accountID = 1;
        BigDecimal amount = BigDecimal.valueOf(1000);

        bank.registerAccount(accountID, amount);
        Account result = bank.getAccount(accountID);

        // Not ideal, but okay for this case
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(new Account(BigDecimal.valueOf(1000)));
    }

    @Test
    public void testRegisterAccount_Overwrite() {
        int accountID = 1;
        BigDecimal initialAmount = BigDecimal.valueOf(1000);
        BigDecimal newAmount = BigDecimal.valueOf(2000);

        bank.registerAccount(accountID, initialAmount);
        bank.registerAccount(accountID, newAmount);
        Account result = bank.getAccount(accountID);

        assertThat(result.getMoney())
                .isEqualTo(BigDecimal.valueOf(2000));
    }

    @Test
    public void testGetAccount_NonExistent() {
        int nonExistentAccountID = 999;
        Account result = bank.getAccount(nonExistentAccountID);

        assertThat(result)
                .isNull();
    }
}