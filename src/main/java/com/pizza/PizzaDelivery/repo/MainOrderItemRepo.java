package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainOrderItemRepo  extends JpaRepository<MainOrderItem,String> {
}
