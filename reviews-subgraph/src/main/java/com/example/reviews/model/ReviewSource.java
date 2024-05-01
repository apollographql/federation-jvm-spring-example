package com.example.reviews.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ReviewSource {

  private static final Map<Long, List<Review>> reviewMap = Map.of(
    2L, List.of(new Review("1020", "Very cramped :( Do not recommend.", 2), new Review("1021", "Got me to the Moon!", 4)),
    3L, List.of(new Review("1030", 3)),
    4L, List.of(new Review("1040", 5), new Review("1041", "Reusable!", 5), new Review("1042", 5)),
    5L, List.of(new Review("1050", "Amazing! Would Fly Again!", 5), new Review("1051", 5))
  );

  public static List<Review> getReviews(Product product) {
    return reviewMap.getOrDefault(product.id(), Collections.emptyList());
  }

}
