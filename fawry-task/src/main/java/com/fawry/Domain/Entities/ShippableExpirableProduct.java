package com.fawry.Domain.Entities;

import java.time.LocalDate;

import com.fawry.Domain.Interfaces.Expirable;
import com.fawry.Domain.Interfaces.Shippable;

public class ShippableExpirableProduct extends Product implements Shippable, Expirable {
  double weight;
  LocalDate expirationDate;

  public ShippableExpirableProduct(int quantity, String name, double price, LocalDate expirationDate, double weight) {
    super(quantity, name, price);
    if (weight <= 0) {
      throw new IllegalArgumentException("Weight must be greater than zero.");
    }
    if (expirationDate == null || expirationDate.isBefore(LocalDate.now().plusDays(1))) {
      throw new IllegalArgumentException("Expiration date must be a future date.");
    }
    this.weight = weight;
    this.expirationDate = expirationDate;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    if (weight <= 0) {
      throw new IllegalArgumentException("Weight must be greater than zero.");
    }
    this.weight = weight;
  }

  @Override
  public boolean isAvailable() {
    return quantity > 0 && weight > 0 && !isExpired();
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
