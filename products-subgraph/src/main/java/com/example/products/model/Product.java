package com.example.products.model;

/**
 * type Product @key(fields: "id") {
 *     id: ID!
 *     name: String!
 *     description: String
 * }
 */
public record Product(Long id, String name, String description) {

  public Product(Long id, String name) {
    this(id, name, null);
  }
}
