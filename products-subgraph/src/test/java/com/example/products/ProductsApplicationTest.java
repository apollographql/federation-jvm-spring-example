package com.example.products;

import com.example.products.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsApplicationTest {

  @LocalServerPort
  int serverPort;

  @Test
  public void verifiesProductQuery() {
    WebTestClient testClient = WebTestClient.bindToServer()
      .baseUrl("http://localhost:" + serverPort + "/graphql")
      .build();
    HttpGraphQlTester tester = HttpGraphQlTester.create(testClient);

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
      .isEqualTo(new Product("1","Saturn V", "The Original Super Heavy-Lift Rocket!"));
  }
}
