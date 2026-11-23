package com.bankingsystem.model;

import java.util.Objects;

public class Customer {

    private final String customerId;
    private final String name;
    private final String phone;

    public Customer(String customerId, String name, String phone) {

        if (customerId == null || customerId.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer ID and name are required.");
        }

        this.customerId = customerId.trim();
        this.name = name.trim();
        this.phone = phone == null ? "" : phone.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Customer{id='%s', name='%s', phone='%s'}".formatted(
            customerId, name, phone
        );
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Customer c)) {
            return false;
        }

        return customerId.equals(c.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}