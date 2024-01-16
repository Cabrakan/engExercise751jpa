package ch.engenius.bank;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BankRunner {
    private final static int numberOfAccounts = 100;
    private final ExecutorService executor = Executors.newFixedThreadPool(8);
    private final Random random = new Random(43);
    private final Bank bank = new Bank();


    public static void main(String[] args) {
        BankRunner runner = new BankRunner();
        BigDecimal defaultDeposit = BigDecimal.valueOf(1000);
        BigDecimal totalExpectedMoney = defaultDeposit.multiply(BigDecimal.valueOf(numberOfAccounts));

        runner.registerAccounts(defaultDeposit);
        runner.sanityCheck(totalExpectedMoney);
        runner.runBank(10000);
        runner.sanityCheck(totalExpectedMoney);
    }

    private void runBank(int iterations) {
        for (int i = 0; i < iterations; i++) {
            executor.submit(this::runRandomOperation);
        }

        try {
            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runRandomOperation() {
        BigDecimal transfer = BigDecimal.valueOf(random.nextDouble() * 100.0);
        int accountInNumber = random.nextInt(numberOfAccounts);
        int accountOutNumber = random.nextInt(numberOfAccounts);

        while (accountInNumber == accountOutNumber) {
            accountInNumber = random.nextInt(numberOfAccounts);
            accountOutNumber = random.nextInt(numberOfAccounts);
        }

        Account accIn = bank.getAccount(accountInNumber);
        Account accOut = bank.getAccount(accountOutNumber);

        accOut.withdraw(transfer);
        accIn.deposit(transfer);
    }

    private void sanityCheck(BigDecimal totalExpectedMoney) {
        BigDecimal sum = IntStream.range(0, numberOfAccounts)
                .mapToObj(bank::getAccount)
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.compareTo(totalExpectedMoney) != 0) {
            throw new IllegalStateException("We got " + sum + " != " + totalExpectedMoney + " (expected).");
        }

        System.out.println("Sanity check OK.");
    }

    private void registerAccounts(BigDecimal defaultMoney) {
        for (int i = 0; i < numberOfAccounts; i++) {
            bank.registerAccount(i, defaultMoney);
        }
    }
}
