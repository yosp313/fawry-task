package com.fawry.Domain.Entities;

public class Customer {
  private String name;
  private String email;
  private double balance;

  public Customer(String name, String email, double balance) {
    this.name = name;
    this.email = email;
    this.balance = balance;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public double getBalance() {
    return balance;
  }

  public void deductBalance(double amount) {
    if (amount <= balance) {
      balance -= amount;
    } else {
      throw new IllegalArgumentException("Insufficient balance");
    }
  }

}
