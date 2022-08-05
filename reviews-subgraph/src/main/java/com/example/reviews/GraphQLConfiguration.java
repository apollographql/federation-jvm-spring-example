package com.example.reviews;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.example.reviews.model.Review;
import graphql.collect.ImmutableMapWithNullValues;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DelegatingDataFetchingEnvironment;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ArgumentOverrideDataFetchingEnvironment extends DelegatingDataFetchingEnvironment {
  private final Map<String, Object> arguments;

  public ArgumentOverrideDataFetchingEnvironment(
    DataFetchingEnvironment delegateEnvironment,
    Map<String, Object> arguments) {
    super(delegateEnvironment);
    this.arguments = arguments;
  }

  @Override
  public Map<String, Object> getArguments() {
    return ImmutableMapWithNullValues.copyOf(arguments);
  }

  @Override
  public boolean containsArgument(String name) {
    return arguments.containsKey(name);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getArgument(String name) {
    return (T) arguments.get(name);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getArgumentOrDefault(String name, T defaultValue) {
    return (T) arguments.getOrDefault(name, defaultValue);
  }
}

class EntityDataFetcher implements DataFetcher<Object> {

  private final RuntimeWiring wiring;

  public EntityDataFetcher(RuntimeWiring wiring) {
    this.wiring = wiring;
  }

  @Override
  public Object get(DataFetchingEnvironment env) throws Exception {
    List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
    List<String> ids = new ArrayList<>(representations.size());

    for (Map<String, Object> representation : representations) {
      String typename = (String)representation.get("__typename");

      if (Review.class.getSimpleName().equals(typename)) {
        ids.add((String)representation.get("id"));
      } else {
        throw new IllegalArgumentException("Unknown entity type: " + typename);
      }
    }

    if (!ids.isEmpty()) {
      var dataFetcher = wiring.getDataFetcherForType("Query").get("getReviewByIds");

      return dataFetcher.get(
        new ArgumentOverrideDataFetchingEnvironment(env, Map.of("ids", ids))
      );
    }

    return null;
  }
}

@Configuration
public class GraphQLConfiguration {

  @Bean
  public GraphQlSourceBuilderCustomizer federationTransform() {
    TypeResolver entityTypeResolver = env ->
      env.getSchema().getObjectType(env.getObject().getClass().getSimpleName());

    return builder -> {
      builder.schemaFactory((registry, wiring) ->
        Federation.transform(registry, wiring)
          .fetchEntities(new EntityDataFetcher(wiring))
          .resolveEntityType(entityTypeResolver)
          .build()
      );
    };
  }
}
