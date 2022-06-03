# Federation JVM Spring Example

[Apollo Federation JVM](https://github.com/apollographql/federation-jvm) example implementation using [Spring GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/).

The repository contains three separate projects:

1. `products-subgraph`: A Java GraphQL service providing the federated `Product` type
2. `reviews-subgraph`: A Java GraphQL service that extends the `Product` type with `reviews`
3. `gateway`: An instance of Apollo Server acting as the Federated Gateway

See individual projects READMEs for detailed instructions on how to run them.

Running the demo
----

1. Start `products-subgraph` by running the Spring Boot app from the IDE or by running `./gradlew bootRun` from `products-subgraph` project
2. Start `reviews-subgraph` by running the Spring Boot app from the IDE or `./gradlew bootRun` from `reviews-subgraph` project
3. Start Federated Gateway
   1. Install required packages by running `npm install` in the `gateway` project
   2. Start gateway by running `npm run start` in the `gateway` project
4. Open http://localhost:4000 for the query editor

Example federated query

```graphql
query ExampleQuery {
    products {
        id
        name
        description
        reviews {
            id
            text
            starRating
        }
    }
}
```

## Other Federation JVM examples

* [Netflix DGS Federation Example](https://github.com/Netflix/dgs-federation-example)
* [GraphQL Java Kickstart Federation Example](https://github.com/setchy/graphql-java-kickstart-federation-example)
