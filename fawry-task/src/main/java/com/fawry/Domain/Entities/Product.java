package com.fawry.Domain.Entities;

public abstract class Product {
  String name;
  double price;
  int quantity;

  public Product(int quantity, String name, double price) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative.");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
    if (price < 0) {
      throw new IllegalArgumentException("Price cannot be negative.");
    }

    this.quantity = quantity;
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    if (price < 0) {
      throw new IllegalArgumentException("Price cannot be negative.");
    }
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative.");
    }
    this.quantity = quantity;
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
