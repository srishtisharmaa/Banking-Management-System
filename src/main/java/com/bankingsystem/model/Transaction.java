package com.bankingsystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {

    public enum Type {
        DEPOSIT, WITHDRAWAL, TRANSFER
    }

    private final String transactionId = UUID.randomUUID()
            .toString()
            .substring(0, 8)
            .toUpperCase();

    private final Type type;
    private final String accountNumber;
    private final String details;
    private final BigDecimal amount;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public Transaction(Type type, String accountNumber, BigDecimal amount, String details) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.details = details;
    }

    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public String toString() {
        return "%s | %s | %s | %s | %s".formatted(
            timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            transactionId,
            type,
            accountNumber,
            amount + " (" + details + ")"
        );
    }
}