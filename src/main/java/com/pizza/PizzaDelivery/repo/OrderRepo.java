package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {
}
