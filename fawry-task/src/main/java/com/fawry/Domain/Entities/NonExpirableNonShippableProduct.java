package com.fawry.Domain.Entities;

public class NonExpirableNonShippableProduct extends Product {
  public NonExpirableNonShippableProduct(int quantity, String name, double price) {
    super(quantity, name, price);
  }

  public boolean isAvailable() {
    return quantity > 0;
  }
}
