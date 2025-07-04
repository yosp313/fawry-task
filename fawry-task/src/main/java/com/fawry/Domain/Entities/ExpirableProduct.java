package com.fawry.Domain.Entities;

import java.time.LocalDate;

import com.fawry.Domain.Interfaces.Expirable;

public class ExpirableProduct extends Product implements Expirable {
  LocalDate expirationDate;

  public ExpirableProduct(int quantity, String name, double price, java.time.LocalDate expirationDate) {
    super(quantity, name, price);

    if (expirationDate == null || expirationDate.isBefore(LocalDate.now().plusDays(1))) {
      throw new IllegalArgumentException("Expiration date must be a future date.");
    }
    this.expirationDate = expirationDate;
  }

  @Override
  public boolean isAvailable() {
    return quantity > 0 && !isExpired();
  }

  @Override
  public boolean isExpired() {
    return LocalDate.now().isAfter(expirationDate);
  }

  @Override
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    if (expirationDate == null || expirationDate.isBefore(LocalDate.now().plusDays(1))) {
      throw new IllegalArgumentException("Expiration date must be a future date.");
    }

    this.expirationDate = expirationDate;
  }
}
