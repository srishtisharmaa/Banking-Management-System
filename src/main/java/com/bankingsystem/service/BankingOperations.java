package com.bankingsystem.service;

import com.bankingsystem.model.*;

import java.math.BigDecimal;
import java.util.List;

public interface BankingOperations {

    void addCustomer(Customer customer);

    void openAccount(BankAccount account);

    void deposit(String accountNo, BigDecimal amount);

    void withdraw(String accountNo, BigDecimal amount);

    void transfer(String from, String to, BigDecimal amount);

    BankAccount findAccount(String accountNo);

    List<BankAccount> getAccounts();

    List<Transaction> getTransactions();
}