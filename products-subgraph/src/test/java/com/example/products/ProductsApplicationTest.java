package com.example.products;

import com.example.products.model.Product;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

@AutoConfigureHttpGraphQlTester
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsApplicationTest {

  @Autowired
  private HttpGraphQlTester tester;

  @Test
  public void verifiesProductQuery() {
    String query = """
      query ProductById($productId: ID!) {
        product(id: $productId) {
          id
          name
          description
        }
      }
      """;

    // new Product("1","Saturn V", "The Original Super Heavy-Lift Rocket!"),
    tester.document(query)
      .variable("productId", "1")
      .execute()
      .path("product")
      .entity(Product.class)
      .isEqualTo(new Product(1L,"Saturn V", "The Original Super Heavy-Lift Rocket!"));
  }
}
