# Federation JVM Spring Example

[Apollo Federation JVM](https://github.com/apollographql/federation-jvm) example implementation using [Spring for GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/).
If you want to discuss the project or just say hi, stop by [the Apollo community forums](https://community.apollographql.com/).

The repository contains two separate projects:

1. `products-subgraph`: A Java GraphQL service providing the federated `Product` type
2. `reviews-subgraph`: A Java GraphQL service that extends the `Product` type with `reviews`

See individual projects READMEs for detailed instructions on how to run them.

Running the demo
----

1. Start `products-subgraph` by running the `ProductsApplication` Spring Boot app from the IDE or by running `./gradlew :products-subgraph:bootRun` from the root project directory
2. Start `reviews-subgraph` by running the `ReviewsApplication` Spring Boot app from the IDE or `./gradlew :reviews-subgraph:bootRun` from the root project directory
3. Start Federated Router
   1. Install [rover CLI](https://www.apollographql.com/docs/rover/getting-started)
   2. Start router and compose products schema using [rover dev command](https://www.apollographql.com/docs/rover/commands/dev)

    ```shell
    # start up router and compose products schema
    rover dev --name products --schema ./products-subgraph/src/main/resources/graphql/schema.graphqls --url http://localhost:8080/graphql
    ```

   3. In **another** shell run `rover dev` to compose reviews schema

    ```shell
    rover dev --name reviews --schema ./reviews-subgraph/src/main/resources/graphql/schema.graphqls --url http://localhost:8081/graphql
    ```

4. Open http://localhost:3000 for the query editor

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
