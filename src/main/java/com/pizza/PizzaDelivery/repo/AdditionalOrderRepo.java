package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.AdditionalOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalOrderRepo extends JpaRepository<AdditionalOrderItem, String> {
}
