package ch.engenius.bank;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BankRunner {
    private static final ExecutorService executor = Executors.newFixedThreadPool(8);
    private final Random random = new Random(43);
    private final Bank bank = new Bank();


    public static void main(String[] args) {
        BankRunner runner = new BankRunner();
        BigDecimal defaultDeposit = BigDecimal.valueOf(1000);
        int accounts = 100;
        int iterations = 10000;

        runner.registerAccounts(accounts, defaultDeposit);
        runner.sanityCheck(accounts, defaultDeposit.multiply(BigDecimal.valueOf(accounts)));
        runner.runBank(iterations, accounts);
        runner.sanityCheck(accounts, defaultDeposit.multiply(BigDecimal.valueOf(accounts)));
    }

    private void runBank(int iterations, int maxAccount) {
        for (int i = 0; i < iterations; i++) {
            executor.submit(() -> runRandomOperation(maxAccount));
        }

        try {
            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runRandomOperation(int maxAccount) {
        BigDecimal transfer = BigDecimal.valueOf(random.nextDouble() * 100.0);
        int accountInNumber = random.nextInt(maxAccount);
        int accountOutNumber = random.nextInt(maxAccount);

        while (accountInNumber == accountOutNumber) {
            accountInNumber = random.nextInt(maxAccount);
            accountOutNumber = random.nextInt(maxAccount);
        }

        Account accIn = bank.getAccount(accountInNumber);
        Account accOut = bank.getAccount(accountOutNumber);

        accIn.deposit(transfer);
        accOut.withdraw(transfer);
    }

    private void sanityCheck(int accountMaxNumber, BigDecimal totalExpectedMoney) {
        BigDecimal sum = IntStream.range(0, accountMaxNumber)
                .mapToObj(bank::getAccount)
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.compareTo(totalExpectedMoney) != 0) {
            throw new IllegalStateException("we got " + sum + " != " + totalExpectedMoney + " (expected)");
        }

        System.out.println("Sanity check OK.");
    }

    private void registerAccounts(int number, BigDecimal defaultMoney) {
        for (int i = 0; i < number; i++) {
            bank.registerAccount(i, defaultMoney);
        }
    }
}
