package com.fawry.Domain.Entities;

public class DigitalProduct extends Product {
  public DigitalProduct(int quantity, String name, double price) {
    super(quantity, name, price);
  }

  public boolean isAvailable() {
    return quantity > 0;
  }
}
