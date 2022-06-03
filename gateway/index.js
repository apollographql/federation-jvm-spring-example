const { ApolloServer } = require('apollo-server');
const { ApolloGateway } = require('@apollo/gateway')

const gateway = new ApolloGateway({
    serviceList: [
        { name: 'products', url: 'http://localhost:8080/graphql' },
        { name: 'reviews', url: 'http://localhost:8081/graphql' },
    ]
});

const server = new ApolloServer({ gateway, subscriptions:false, tracing:true });

server.listen().then(({ url }) => {
  console.log(`🚀 Server ready at ${url}`);
});
