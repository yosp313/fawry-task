package com.fawry.Domain.Entities;

import com.fawry.Domain.Interfaces.Shippable;

public class ShippableProduct extends Product implements Shippable {
  double weight;

  public ShippableProduct(int quantity, String name, double price, double weight) {
    super(quantity, name, price);

    if (weight <= 0) {
      throw new IllegalArgumentException("Weight must be greater than zero.");
    }
    this.weight = weight;
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
    return quantity > 0 && weight > 0;
  }
}
