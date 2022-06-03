# Apollo Federation

This is a simple Apollo Federation Gateway server. For more info see the Apollo docs

https://www.apollographql.com/docs/federation/

## Setup

Install the correct version of Node locally. You can also use [nvm](https://github.com/nvm-sh/nvm)

```shell script
nvm i
```

Install all dependent packages.

```shell script
npm install
```

Then start the other applications

* `products` should run on port 8080
* `reviews` should run on port 8081

Then you can start the gateway
```shell script
npm run start
```

Once the Gateway has started you can explore the example federated schema by opening Apollo Studio endpoint at http://localhost:4000.
