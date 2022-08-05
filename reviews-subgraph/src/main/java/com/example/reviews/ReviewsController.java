package com.example.reviews;

import com.example.reviews.model.Review;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ReviewsController {

  private final Map<String, Review> REVIEWS = Map.of(
    "1020", new Review("1020", "Very cramped :( Do not recommend.", 2),
    "1021", new Review("1021", "Got me to the Moon!", 4),
    "1030", new Review("1030", 3),
    "1040", new Review("1040", 5),
    "1041", new Review("1041", "Reusable!", 5),
    "1042", new Review("1042", 5),
    "1050", new Review("1050", "Amazing! Would Fly Again!", 5),
    "1051", new Review("1051", 5)
  );

  @QueryMapping
  public List<Review> getReviewByIds(@Argument List<String> ids) {
    return ids.stream().map(REVIEWS::get).collect(Collectors.toList());
  }
}
