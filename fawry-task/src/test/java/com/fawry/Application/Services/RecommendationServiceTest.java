package com.fawry.Application.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fawry.Domain.Entities.Cart;
import com.fawry.Domain.Entities.NonExpirableNonShippableProduct;
import com.fawry.Domain.Entities.Product;
import com.fawry.Domain.Entities.ShippableExpirableProduct;
import com.fawry.Domain.Entities.ShippableProduct;

public class RecommendationServiceTest {

  private RecommendationService recommendationService;
  private Cart cart;

  @BeforeEach
  public void setUp() {
    recommendationService = new RecommendationService();
    cart = new Cart("test-cart", "test-customer", 0.0);
  }

  @Test
  public void testEmptyCartReturnsDefaultRecommendations() {
    List<Product> recommendations = recommendationService.getRecommendations(cart);
    
    assertFalse(recommendations.isEmpty());
    assertEquals(2, recommendations.size());
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Bestseller Book")));
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Gift Card")));
  }

  @Test
  public void testElectronicsCartReturnsElectronicsAccessories() {
    Product laptop = new ShippableProduct(1, "Laptop", 500.0, 2.0);
    cart.add(laptop, 1);
    
    List<Product> recommendations = recommendationService.getRecommendations(cart);
    
    assertFalse(recommendations.isEmpty());
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Wireless Mouse")));
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("USB Cable")));
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Software License")));
  }

  @Test
  public void testFoodCartReturnsFoodRecommendations() {
    Product milk = new ShippableExpirableProduct(1, "Milk", 10.0, LocalDate.of(2025, 10, 10), 1.0);
    cart.add(milk, 1);
    
    List<Product> recommendations = recommendationService.getRecommendations(cart);
    
    assertFalse(recommendations.isEmpty());
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Cheese")));
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Butter")));
  }

  @Test
  public void testExpensiveItemsReturnProtectionProducts() {
    Product expensiveItem = new ShippableProduct(1, "Premium Device", 300.0, 1.0);
    cart.add(expensiveItem, 1);
    
    List<Product> recommendations = recommendationService.getRecommendations(cart);
    
    assertFalse(recommendations.isEmpty());
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Extended Warranty")));
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Protective Case")));
  }

  @Test
  public void testMixedCartReturnsCombinedRecommendations() {
    Product smartphone = new ShippableProduct(1, "Smartphone", 300.0, 0.5);
    Product bread = new ShippableExpirableProduct(1, "Bread", 2.0, LocalDate.of(2025, 10, 15), 0.2);
    
    cart.add(smartphone, 1);
    cart.add(bread, 1);
    
    List<Product> recommendations = recommendationService.getRecommendations(cart);
    
    assertFalse(recommendations.isEmpty());
    // Should include electronics accessories
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Wireless Mouse")));
    // Should include food items
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Cheese")));
    // Should include protection for expensive item
    assertTrue(recommendations.stream().anyMatch(p -> p.getName().equals("Extended Warranty")));
  }
}