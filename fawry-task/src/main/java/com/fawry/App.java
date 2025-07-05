package com.fawry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fawry.Application.Services.ShippingService;
import com.fawry.Domain.Entities.Cart;
import com.fawry.Domain.Entities.Customer;
import com.fawry.Domain.Entities.NonExpirableNonShippableProduct;
import com.fawry.Domain.Entities.Product;
import com.fawry.Domain.Entities.ShippableExpirableProduct;
import com.fawry.Domain.Entities.ShippableProduct;
import com.fawry.Domain.Interfaces.Expirable;
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
    System.out.println("Processing checkout...");

    if (cart.isEmpty()) {
      System.err.println("Checkout Error: Cart is empty.");
      return;
    }

    double subtotal = 0;
    List<Shippable> itemsToShip = new ArrayList<>();

    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();

      if (product.getQuantity() < quantity) {
        System.err.printf("Checkout Error: Product '%s' is out of stock.%n", product.getName());
        return;
      }

      if (product instanceof Expirable) {
        if (((Expirable) product).isExpired()) {
          System.err.printf("Checkout Error: Product '%s' is expired.%n", product.getName());
          return;
        }
      }

      // Add product price to subtotal
      subtotal += product.getPrice() * quantity;

      // Collect shippable items
      if (product instanceof Shippable) {
        // Add the item N times for N quantities for shipping purposes
        for (int i = 0; i < quantity; i++) {
          itemsToShip.add((Shippable) product);
        }
      }
    }

    double shippingFee = itemsToShip.isEmpty() ? 0 : 50.00;
    double totalAmount = subtotal + shippingFee;

    if (customer.getBalance() < totalAmount) {
      System.err.println("Checkout Error: Insufficient customer balance.");
      return;
    }

    customer.deductBalance(totalAmount);

    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();
      product.decreaseQuantity(quantity);
    }

    ShippingService shippingService = new ShippingService();
    shippingService.ship(itemsToShip);

    System.out.println("** Checkout Receipt **");
    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();
      System.out.printf("- %dx %s @ $%.2f each = $%.2f%n", quantity, product.getName(), product.getPrice(),
          product.getPrice() * quantity);
    }
    System.out.println("-------------------------");
    System.out.printf("Subtotal:         $%.2f%n", subtotal);
    System.out.printf("Shipping Fees:    $%.2f%n", shippingFee);
    System.out.printf("Paid Amount:      $%.2f%n", totalAmount);
    System.out.println("-------------------------");
    System.out.printf("Remaining Balance: $%.2f%n", customer.getBalance());
    System.out.println("\nCheckout successful!");
  }
}
