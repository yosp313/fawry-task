package com.fawry;

import java.time.LocalDate;
import java.util.Map;

import com.fawry.Domain.Entities.Cart;
import com.fawry.Domain.Entities.Customer;
import com.fawry.Domain.Entities.NonExpirableNonShippableProduct;
import com.fawry.Domain.Entities.Product;
import com.fawry.Domain.Entities.ShippableExpirableProduct;
import com.fawry.Domain.Entities.ShippableProduct;
import com.fawry.Domain.Interfaces.Shippable;

public class App {
  public static void main(String[] args) {
    Customer customer = new Customer("John Doe", "john@email.com", 1000);

    Product product1 = new ShippableProduct(3, "Laptop", 500.0, 5.0);
    Product product2 = new ShippableProduct(2, "Smartphone", 300.0, 0.5);
    Product product5 = new ShippableExpirableProduct(10, "Milk", 10.0, LocalDate.of(2025, 10, 10), 4.0);
    Product product3 = new ShippableExpirableProduct(
        1, "Bread", 2.0, LocalDate.of(2025, 10, 15), 0.2);
    Product product4 = new NonExpirableNonShippableProduct(5, "Mobile Scratch Card", 50.0);

    Cart cart = new Cart("cart123", "customer123", 0.0);

    cart.add(product1, 1);
    cart.add(product2, 2);
    cart.add(product3, 1);
    cart.add(product4, 1);
    cart.add(product5, 1);
    cart.remove(product2, 1);

    checkout(customer, cart);
  }

  public static void checkout(Customer customer, Cart cart) {

    if (cart.isEmpty()) {
      System.err.print("Checkout Error: Cart is empty.\n");
      return;
    }

    double subtotal = cart.getTotalAmount();
    double shippingFee = 30.0;
    double totalAmount = subtotal + shippingFee;
    if (customer.getBalance() < totalAmount) {
      System.err.print("Checkout Error: Insufficient customer balance.\n");
      return;
    }

    System.out.print("** Shipment notice **\n");
    double totalWeight = 0;
    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      if (entry.getKey() instanceof Shippable) {
        Shippable shippableItem = (Shippable) entry.getKey();
        int quantity = entry.getValue();
        double itemTotalWeight = shippableItem.getWeight() * quantity;
        totalWeight += itemTotalWeight;

        System.out.print(quantity + "x " + entry.getKey().getName() + "\t");
        System.out.print((int) (itemTotalWeight * 1000) + "g\n");
      }
    }
    System.out.print("Total package weight " + totalWeight + "kg\n");

    System.out.print("\n");

    System.out.print("** Checkout receipt **\n");
    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();
      System.out.print(quantity + "x " + product.getName() + "\t");
      System.out.print((int) (product.getPrice() * quantity) + "\n");
    }

    System.out.print("--------------------------\n");

    System.out.print("Subtotal" + "\t");
    System.out.print((int) subtotal + "\n");
    System.out.print("Shipping\t");
    System.out.print((int) shippingFee + "\n");
    System.out.print("Amount\t");
    System.out.print((int) totalAmount + "\n");

    customer.deductBalance(totalAmount);
    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();
      product.decreaseQuantity(quantity);
    }
  }
}
