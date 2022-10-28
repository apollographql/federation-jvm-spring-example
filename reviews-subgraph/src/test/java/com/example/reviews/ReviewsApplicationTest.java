package com.example.reviews;

import com.example.reviews.model.Review;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewsApplicationTest {

  @LocalServerPort
  int serverPort;

  @Test
  public void verifiesProductQuery() {
    WebTestClient testClient = WebTestClient.bindToServer()
      .baseUrl("http://localhost:" + serverPort + "/graphql")
      .build();
    HttpGraphQlTester tester = HttpGraphQlTester.create(testClient);

    String query = """
      query Entities($representations: [_Any!]!) {
        _entities(representations: $representations) {
          ...on Product {
            id
            reviews {
              id
              text
              starRating
            }
          }
        }
      }
      """;

    tester.document(query)
      .variable("representations", List.of(Map.of("__typename", "Product", "id", "5")))
      .execute()
      .path("_entities[0].id").entity(String.class).isEqualTo("5")
      .path("_entities[0].reviews[0]")
      .entity(Review.class)
      .isEqualTo(new Review("1050", "Amazing! Would Fly Again!", 5));
  }
}
