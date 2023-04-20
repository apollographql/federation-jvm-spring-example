package com.example.products;

import com.example.products.model.Product;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ProductsController {

  private final Map<String, Product> PRODUCTS = Stream.of(
    new Product("1","Saturn V", "The Original Super Heavy-Lift Rocket!"),
    new Product("2","Lunar Module"),
    new Product("3","Space Shuttle"),
    new Product("4","Falcon 9", "Reusable Medium-Lift Rocket"),
    new Product("5","Dragon", "Reusable Medium-Lift Rocket"),
    new Product("6","Starship", "Super Heavy-Lift Reusable Launch Vehicle")
  ).collect(Collectors.toMap(Product::id, product -> product));

  @QueryMapping
  public Product product(@Argument String id) {
    return PRODUCTS.get(id);
  }

  @QueryMapping
  public List<Product> products() {
    return PRODUCTS.values().stream().toList();
  }
}
