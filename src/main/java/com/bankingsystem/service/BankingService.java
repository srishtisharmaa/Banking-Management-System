package com.bankingsystem.service;

import com.bankingsystem.exception.*;
import com.bankingsystem.model.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BankingService implements BankingOperations {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();
    private final Map<String, BankAccount> accounts = new ConcurrentHashMap<>();
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();

    private final Object transferLock = new Object();

    @Override
    public void addCustomer(Customer c) {

        if (customers.putIfAbsent(c.getCustomerId(), c) != null) {
            throw new BankingException("Customer ID already exists.");
        }
    }

    @Override
    public void openAccount(BankAccount a) {

        if (!customers.containsKey(a.getCustomer().getCustomerId())) {
            throw new BankingException("Register customer before opening an account.");
        }

        if (accounts.putIfAbsent(a.getAccountNumber(), a) != null) {
            throw new DuplicateAccountException("Account number already exists.");
        }
    }

    @Override
    public void deposit(String no, BigDecimal amount) {

        BankAccount a = findAccount(no);
        a.deposit(amount);

        transactions.add(
            new Transaction(Transaction.Type.DEPOSIT, no, amount, "Cash deposit")
        );
    }

    @Override
    public void withdraw(String no, BigDecimal amount) {

        BankAccount a = findAccount(no);
        a.withdraw(amount);

        transactions.add(
            new Transaction(Transaction.Type.WITHDRAWAL, no, amount, "Cash withdrawal")
        );
    }

    @Override
    public void transfer(String from, String to, BigDecimal amount) {

        if (from.equals(to)) {
            throw new BankingException("Source and destination accounts must be different.");
        }

        BankAccount source = findAccount(from);
        BankAccount destination = findAccount(to);

        synchronized (transferLock) {
            source.withdraw(amount);
            destination.deposit(amount);
        }

        transactions.add(
            new Transaction(Transaction.Type.TRANSFER, from, amount, "Transferred to " + to)
        );

        transactions.add(
            new Transaction(Transaction.Type.TRANSFER, to, amount, "Received from " + from)
        );
    }

    @Override
    public BankAccount findAccount(String no) {

        BankAccount a = accounts.get(no);

        if (a == null) {
            throw new AccountNotFoundException("Account not found: " + no);
        }

        return a;
    }

    @Override
    public List<BankAccount> getAccounts() {
        return List.copyOf(accounts.values());
    }

    @Override
    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    public int getCustomerCount() {
        return customers.size();
    }
}