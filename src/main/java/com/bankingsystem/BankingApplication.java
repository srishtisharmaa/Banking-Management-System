package com.bankingsystem;

import com.bankingsystem.model.*;
import com.bankingsystem.service.*;
import com.bankingsystem.exception.BankingException;

import java.math.BigDecimal;
import java.util.Scanner;

public class BankingApplication {

    private static final BankingService bank = new BankingService();
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        seed();

        boolean running = true;

        while (running) {

            System.out.println(
                "\n=== BANKING MANAGEMENT SYSTEM ===\n" +
                "1. Create customer and account\n" +
                "2. Deposit\n" +
                "3. Withdraw\n" +
                "4. Transfer\n" +
                "5. Check balance\n" +
                "6. List accounts\n" +
                "7. Transaction history\n" +
                "8. Run concurrent deposit demo\n" +
                "0. Exit\n" +
                "Choose: "
            );

            try {
                switch (in.nextLine().trim()) {

                    case "1" -> createAccount();

                    case "2" -> {
                        System.out.print("Account number: ");
                        String n = in.nextLine();

                        System.out.print("Amount: ");
                        bank.deposit(n, new BigDecimal(in.nextLine()));

                        System.out.println("Deposit successful.");
                    }

                    case "3" -> {
                        System.out.print("Account number: ");
                        String n = in.nextLine();

                        System.out.print("Amount: ");
                        bank.withdraw(n, new BigDecimal(in.nextLine()));

                        System.out.println("Withdrawal successful.");
                    }

                    case "4" -> {
                        System.out.print("From: ");
                        String f = in.nextLine();

                        System.out.print("To: ");
                        String t = in.nextLine();

                        System.out.print("Amount: ");
                        bank.transfer(f, t, new BigDecimal(in.nextLine()));

                        System.out.println("Transfer successful.");
                    }

                    case "5" -> {
                        System.out.print("Account number: ");
                        System.out.println(
                            "Balance: " + bank.findAccount(in.nextLine()).getBalance()
                        );
                    }

                    case "6" ->
                        bank.getAccounts().forEach(System.out::println);

                    case "7" ->
                        bank.getTransactions().forEach(System.out::println);

                    case "8" ->
                        concurrentDemo();

                    case "0" ->
                        running = false;

                    default ->
                        System.out.println("Invalid menu option.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");

            } catch (BankingException | IllegalArgumentException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }

        System.out.println("Thank you for using the Banking Management System.");
    }

    private static void seed() {

        Customer c = new Customer("C001", "Aarav Sharma", "9876543210");

        bank.addCustomer(c);

        bank.openAccount(
            new SavingsAccount("1001001", c, new BigDecimal("5000.00"))
        );

        bank.openAccount(
            new CurrentAccount(
                "2002001",
                c,
                new BigDecimal("10000.00"),
                new BigDecimal("2000.00")
            )
        );
    }

    private static void createAccount() {

        System.out.print("Customer ID: ");
        String id = in.nextLine();

        System.out.print("Customer name: ");
        String name = in.nextLine();

        System.out.print("Phone: ");
        String phone = in.nextLine();

        Customer c = new Customer(id, name, phone);

        bank.addCustomer(c);

        System.out.print("Account number: ");
        String no = in.nextLine();

        System.out.print("Type (S=savings, C=current): ");
        String type = in.nextLine();

        System.out.print("Opening balance: ");
        BigDecimal opening = new BigDecimal(in.nextLine());

        BankAccount a = type.equalsIgnoreCase("C")
            ? new CurrentAccount(no, c, opening, new BigDecimal("1000.00"))
            : new SavingsAccount(no, c, opening);

        bank.openAccount(a);

        System.out.println("Account created: " + a);
    }

    private static void concurrentDemo() {

        BankAccount a = bank.findAccount("1001001");

        int workers = 5;

        Thread[] threads = new Thread[workers];

        for (int i = 0; i < workers; i++) {

            threads[i] = new Thread(
                () -> bank.deposit(
                    a.getAccountNumber(),
                    new BigDecimal("100.00")
                ),
                "Deposit-" + (i + 1)
            );

            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Demo interrupted.");
                return;
            }
        }

        System.out.println(
            "Concurrent deposits completed. New balance: " + a.getBalance()
        );
    }
}