# Products Subgraph

[Apollo Federation JVM](https://github.com/apollographql/federation-jvm) example subgraph implementation using [Spring GraphQL](https://docs.spring.io/spring-graphql/docs/current/reference/html/) and exposing the federated `Product` type.

```graphql
type Query {
    product(id: ID!): Product
    products: [Product!]!
}

type Product @key(fields: "id") {
    id: ID!
    name: String!
    description: String
}
```

### Running locally
Build the application by running the following from the project directory:

```shell
./gradlew build
```

> NOTE: in order to ensure you use the right version of Gradle we highly recommend to use the provided wrapper scripts

Start the server:

* Run `ProductsApplication.java` directly from your IDE
* Alternatively you can also use the Gradle Spring Boot plugin

```shell
./gradlew bootRun
```

Once the app has started you can explore the example schema by opening GraphiQL endpoint at http://localhost:8080/graphiql.
