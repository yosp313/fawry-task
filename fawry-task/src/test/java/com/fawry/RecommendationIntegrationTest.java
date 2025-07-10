package com.fawry;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.fawry.Domain.Entities.Cart;
import com.fawry.Domain.Entities.Customer;
import com.fawry.Domain.Entities.Product;
import com.fawry.Domain.Entities.ShippableProduct;

public class RecommendationIntegrationTest {

  @Test
  public void testCheckoutWithRecommendationsDisplaysCorrectly() {
    // Capture console output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      // Set up test data
      Customer customer = new Customer("Test Customer", "test@email.com", 1000);
      Cart cart = new Cart("test-cart", "test-customer", 0.0);
      Product laptop = new ShippableProduct(1, "Laptop", 500.0, 2.0);
      cart.add(laptop, 1);

      // Execute checkout - should not throw any exceptions
      assertDoesNotThrow(() -> {
        App.checkout(customer, cart);
      });

      // Verify output contains recommendation section
      String output = outputStream.toString();
      assert(output.contains("** Recommended for you **"));
      assert(output.contains("Wireless Mouse"));

    } finally {
      // Restore original output stream
      System.setOut(originalOut);
    }
  }
}