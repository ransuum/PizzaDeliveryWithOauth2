package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
}
