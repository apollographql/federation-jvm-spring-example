package com.example.products.model;

import java.util.List;

/**
 * type Product @key(fields: "id") {
 *     id: ID!
 *     name: String!
 *     description: String
 * }
 */
public record Product(String id, String name, String description, List<String> reviewIds) {

  public Product(String id, String name) {
    this(id, name, null, null);
  }
}
