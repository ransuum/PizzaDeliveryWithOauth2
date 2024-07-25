package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
}
