package com.fawry.Domain.Entities;

import java.util.Map;

public class Cart {
  private String cartId;
  private String customerId;
  private double totalAmount;

  private Map<Product, Integer> products;

  public Cart(String cartId, String customerId, double totalAmount) {
    this.cartId = cartId;
    this.customerId = customerId;
    this.totalAmount = totalAmount;
    this.products = new java.util.HashMap<>();
  }

  public String getCartId() {
    return cartId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void add(Product product, int quantity) {
    if (products.containsKey(product)) {
      products.put(product, products.get(product) + quantity);
    } else {
      products.put(product, quantity);
    }
    totalAmount += product.getPrice() * quantity;
  }

  public void remove(Product product, int quantity) {
    if (products.containsKey(product)) {
      int currentQuantity = products.get(product);
      if (currentQuantity <= quantity) {
        products.remove(product);
        totalAmount -= product.getPrice() * currentQuantity;
      } else {
        products.put(product, currentQuantity - quantity);
        totalAmount -= product.getPrice() * quantity;
      }
    }
  }

  public Map<Product, Integer> getProducts() {
    return products;
  }

  public boolean isEmpty() {
    return products.isEmpty();
  }
}
