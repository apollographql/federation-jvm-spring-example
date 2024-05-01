package com.example.products.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ProductSource {

  private static final List<Product> productList = List.of(
    new Product(1L, "Saturn V", "The Original Super Heavy-Lift Rocket!"),
    new Product(2L, "Lunar Module"),
    new Product(3L, "Space Shuttle"),
    new Product(4L, "Falcon 9", "Reusable Medium-Lift Rocket"),
    new Product(5L, "Dragon", "Reusable Medium-Lift Rocket"),
    new Product(6L, "Starship", "Super Heavy-Lift Reusable Launch Vehicle")
  );

  private static final Map<Long, Product> productMap =
    productList.stream().collect(Collectors.toMap(Product::id, product -> product));

  public static Product getProduct(Long id) {
    return productMap.get(id);
  }

  public static List<Product> getProducts() {
    return productList;
  }

}
