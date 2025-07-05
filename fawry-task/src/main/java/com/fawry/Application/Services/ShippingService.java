package com.fawry.Application.Services;

import java.util.List;

import com.fawry.Domain.Entities.Product;
import com.fawry.Domain.Interfaces.Shippable;

public class ShippingService {
  public void ship(List<Shippable> items) {
    if (items.isEmpty()) {
      return;
    }

    System.out.println("** Shipment Notice **");
    double totalWeight = 0;
    for (Shippable item : items) {
      if (item instanceof Product) {
        System.out.printf("- %s%n", ((Product) item).getName());
        System.out.printf("  Weight: %.2fg%n", item.getWeight());
        totalWeight += item.getWeight();
      }
    }
    System.out.printf("Total package weight: %.2fkg%n", totalWeight / 1000.0);
    System.out.println("-------------------------");
  }
}
