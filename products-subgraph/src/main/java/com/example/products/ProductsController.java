package com.example.products;

import com.example.products.model.Product;
import com.example.products.model.Review;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ProductsController {

  private final Map<String, Product> PRODUCTS = Stream.of(
    new Product("1","Saturn V", "The Original Super Heavy-Lift Rocket!", Collections.emptyList()),
    new Product("2","Lunar Module", null, Arrays.asList("1020", "1021")),
    new Product("3","Space Shuttle", null, Arrays.asList("1030")),
    new Product("4","Falcon 9", "Reusable Medium-Lift Rocket", Arrays.asList("1040", "1041", "1042")),
    new Product("5","Dragon", "Reusable Medium-Lift Rocket", Arrays.asList("1050", "1051")),
    new Product("6","Starship", "Super Heavy-Lift Reusable Launch Vehicle", Collections.emptyList())
  ).collect(Collectors.toMap(Product::id, product -> product));

  @QueryMapping
  public Product product(@Argument String id) {
    return PRODUCTS.get(id);
  }

  @QueryMapping
  public List<Product> products() {
    return PRODUCTS.values().stream().toList();
  }

  @SchemaMapping(typeName="Product", field="reviews")
  public List<Review> reviews(Product show) {
    var product = PRODUCTS.get(show.id());

    if (product != null) {
      return product.reviewIds().stream().map(Review::new).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }
}
