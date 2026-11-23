package com.bankingsystem.model;

import com.bankingsystem.exception.InsufficientFundsException;
import com.bankingsystem.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BankAccount {

    private final String accountNumber;
    private final Customer customer;
    private BigDecimal balance;

    protected BankAccount(String accountNumber, Customer customer, BigDecimal openingBalance) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number is required.");
        }

        this.accountNumber = accountNumber.trim();
        this.customer = Objects.requireNonNull(customer);

        if (openingBalance == null || openingBalance.signum() < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative.");
        }

        this.balance = openingBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public synchronized BigDecimal getBalance() {
        return balance;
    }

    public synchronized void deposit(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.add(amount);
    }

    public synchronized void withdraw(BigDecimal amount) {

        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                "Insufficient balance in account " + accountNumber
            );
        }

        balance = balance.subtract(amount);
    }

    protected synchronized void withdrawWithLimit(BigDecimal amount, BigDecimal limit) {

        validateAmount(amount);

        if (balance.add(limit).compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                "Balance and overdraft limit exceeded for account " + accountNumber
            );
        }

        balance = balance.subtract(amount);
    }

    protected void validateAmount(BigDecimal amount) {

        if (amount == null || amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
    }

    public abstract String getAccountType();

    public abstract BigDecimal getMinimumBalance();

    @Override
    public String toString() {
        return "%s{account='%s', customer='%s', balance=%s}".formatted(
            getAccountType(),
            accountNumber,
            customer.getName(),
            getBalance()
        );
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof BankAccount a)) {
            return false;
        }

        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}