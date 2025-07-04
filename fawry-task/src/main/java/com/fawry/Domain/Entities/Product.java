package com.fawry.Domain.Entities;

public abstract class Product {
  final String name;
  final double price;
  int quantity;

  public Product(int quantity, String name, double price) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative.");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
    if (price <= 0) {
      throw new IllegalArgumentException("Price cannot be negative.");
    }

    this.quantity = quantity;
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void increaseQuantity(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity to increase cannot be negative.");
    }
    if (quantity == 0) {
      throw new IllegalArgumentException("Quantity to increase cannot be zero.");
    }
    if (this.quantity + quantity < 0) {
      throw new IllegalArgumentException("Resulting quantity cannot be negative.");
    }

    this.quantity += quantity;

  }

  public void decreaseQuantity(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity to decrease cannot be negative.");
    }
    if (this.quantity < quantity) {
      throw new IllegalArgumentException("Insufficient quantity to decrease.");
    }
    if (quantity == 0) {
      throw new IllegalArgumentException("Quantity to decrease cannot be zero.");
    }
    if (this.quantity - quantity < 0) {
      throw new IllegalArgumentException("Resulting quantity cannot be negative.");
    }

    this.quantity -= quantity;
  }

  public abstract boolean isAvailable();

  @Override
  public String toString() {
    return "Product{" +
        "name='" + name + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        '}';
  }

}
