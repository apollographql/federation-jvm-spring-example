package com.example.reviews;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.example.reviews.model.Product;
import graphql.schema.DataFetcher;
import graphql.schema.TypeResolver;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.reviews.model.Product.PRODUCT_TYPE;

@Configuration
public class GraphQLConfiguration {

  @Bean
  public GraphQlSourceBuilderCustomizer federationTransform() {
    DataFetcher entityDataFetcher = env -> {
      List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
      return representations.stream()
        .map(representation -> {
          if (PRODUCT_TYPE.equals(representation.get("__typename"))) {
            return new Product((String)representation.get("id"));
          }
          return null;
        })
        .collect(Collectors.toList());
    };
    TypeResolver entityTypeResolver = env -> {
      final Object src = env.getObject();
      if (src instanceof Product) {
        return env.getSchema()
          .getObjectType(PRODUCT_TYPE);
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
