package com.example.reviews;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.example.reviews.model.Product;
import com.example.reviews.model.Review;
import graphql.schema.DataFetcher;
import graphql.schema.TypeResolver;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class GraphQLConfiguration {

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

  @Bean
  public GraphQlSourceBuilderCustomizer federationTransform() {
    DataFetcher entityDataFetcher = env -> {
      List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
      return representations.stream()
        .map(representation -> {
          if (Product.class.getSimpleName().equals(representation.get("__typename"))) {
            return new Product((String)representation.get("id"));
          } else if (Review.class.getSimpleName().equals(representation.get("__typename"))) {
            return REVIEWS.get((String)representation.get("id"));
          }
          return null;
        })
        .collect(Collectors.toList());
    };
    TypeResolver entityTypeResolver = env -> {
      final Object src = env.getObject();
      if (src instanceof Product) {
        return env.getSchema().getObjectType(Product.class.getSimpleName());
      } else if (src instanceof Review) {
        return env.getSchema().getObjectType(Review.class.getSimpleName());
      }
      return null;
    };

    return builder -> {
      builder.schemaFactory((registry, wiring)->
        Federation.transform(registry, wiring)
          .fetchEntities(entityDataFetcher)
          .resolveEntityType(entityTypeResolver)
          .build()
      );
    };
  }
}
