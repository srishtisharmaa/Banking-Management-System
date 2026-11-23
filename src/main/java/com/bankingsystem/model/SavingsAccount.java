package com.bankingsystem.model;

import java.math.BigDecimal;

public class SavingsAccount extends BankAccount {

    private static final BigDecimal MINIMUM = new BigDecimal("500.00");
    private final BigDecimal interestRate;

    public SavingsAccount(String no, Customer customer, BigDecimal opening, BigDecimal interestRate) {
        super(no, customer, opening);
        this.interestRate = interestRate;
    }

    public SavingsAccount(String no, Customer customer, BigDecimal opening) {
        this(no, customer, opening, new BigDecimal("0.035"));
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public BigDecimal getMinimumBalance() {
        return MINIMUM;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    @Override
    public String toString() {
        return super.toString() + ", interestRate=" + interestRate;
    }
}