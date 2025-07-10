package com.fawry.Application.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fawry.Domain.Entities.Cart;
import com.fawry.Domain.Entities.NonExpirableNonShippableProduct;
import com.fawry.Domain.Entities.Product;
import com.fawry.Domain.Entities.ShippableExpirableProduct;
import com.fawry.Domain.Entities.ShippableProduct;

public class RecommendationService {

  public List<Product> getRecommendations(Cart cart) {
    List<Product> recommendations = new ArrayList<>();
    
    if (cart.isEmpty()) {
      return getDefaultRecommendations();
    }
    
    boolean hasElectronics = false;
    boolean hasFood = false;
    boolean hasExpensiveItems = false;
    
    for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      
      // Check for electronics
      if (isElectronics(product)) {
        hasElectronics = true;
      }
      
      // Check for food items
      if (isFood(product)) {
        hasFood = true;
      }
      
      // Check for expensive items
      if (product.getPrice() > 200) {
        hasExpensiveItems = true;
      }
    }
    
    // Recommend based on cart contents
    if (hasElectronics) {
      recommendations.addAll(getElectronicsAccessories());
    }
    
    if (hasFood) {
      recommendations.addAll(getComplementaryFood());
    }
    
    if (hasExpensiveItems) {
      recommendations.addAll(getProtectionProducts());
    }
    
    return recommendations;
  }
  
  private boolean isElectronics(Product product) {
    String name = product.getName().toLowerCase();
    return name.contains("laptop") || name.contains("smartphone") || 
           name.contains("phone") || name.contains("computer");
  }
  
  private boolean isFood(Product product) {
    String name = product.getName().toLowerCase();
    return name.contains("milk") || name.contains("bread") || 
           name.contains("food") || name.contains("drink");
  }
  
  private List<Product> getElectronicsAccessories() {
    List<Product> accessories = new ArrayList<>();
    accessories.add(new ShippableProduct(5, "Wireless Mouse", 25.0, 0.2));
    accessories.add(new ShippableProduct(3, "USB Cable", 15.0, 0.1));
    accessories.add(new NonExpirableNonShippableProduct(10, "Software License", 99.0));
    return accessories;
  }
  
  private List<Product> getComplementaryFood() {
    List<Product> food = new ArrayList<>();
    food.add(new ShippableExpirableProduct(8, "Cheese", 8.0, LocalDate.of(2025, 11, 1), 0.3));
    food.add(new ShippableExpirableProduct(12, "Butter", 6.0, LocalDate.of(2025, 10, 20), 0.2));
    return food;
  }
  
  private List<Product> getProtectionProducts() {
    List<Product> protection = new ArrayList<>();
    protection.add(new NonExpirableNonShippableProduct(5, "Extended Warranty", 75.0));
    protection.add(new ShippableProduct(7, "Protective Case", 35.0, 0.3));
    return protection;
  }
  
  private List<Product> getDefaultRecommendations() {
    List<Product> defaults = new ArrayList<>();
    defaults.add(new ShippableProduct(10, "Bestseller Book", 20.0, 0.4));
    defaults.add(new NonExpirableNonShippableProduct(15, "Gift Card", 25.0));
    return defaults;
  }
}