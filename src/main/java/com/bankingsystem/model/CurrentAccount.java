package com.bankingsystem.model;

import java.math.BigDecimal;

public class CurrentAccount extends BankAccount {

    private static final BigDecimal MINIMUM = BigDecimal.ZERO;
    private final BigDecimal overdraftLimit;

    public CurrentAccount(String no, Customer customer, BigDecimal opening, BigDecimal overdraftLimit) {

        super(no, customer, opening);

        if (overdraftLimit == null || overdraftLimit.signum() < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }

        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return MINIMUM;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public synchronized void withdraw(BigDecimal amount) {
        withdrawWithLimit(amount, overdraftLimit);
    }

    @Override
    public String toString() {
        return super.toString() + ", overdraftLimit=" + overdraftLimit;
    }
}