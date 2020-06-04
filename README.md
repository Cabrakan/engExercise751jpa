# Fix and refactor

## Simple bank

The application is built using 2 classes.

Account class models how much money a person has. Two main methods are deposit and withdraw. The Account may never have a negative deposit. (There is an if statement preventing that). Bank class models registering an account and getting the existing account (by id).

Both classes have a lot of bugs.

## Simulation

There is a 3rd class BankRunner which has the main method.
This class runs a simulation, where some number of accounts is created. Then some random transfers are done. This is performed using multiple threads.
After that, the overall amount of money on accounts should not change.

This seems not to be the case.

## Task

Your job is to find bugs and other problems in the implementation.

1. Make corrections to the Account and Bank classes. (if you do not have time, please simply list the problems that you see).
2. You may also have to change BankRunner class! In such a case, please try to preserve its intended logic.
3. Additionally, try to fix or list other potential code quality issues that you see.
4. You are free to create additional classes or files as needed.