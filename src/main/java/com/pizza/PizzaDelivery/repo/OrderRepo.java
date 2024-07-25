package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.Orders;
import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.dto.UserInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Orders, String> {
    Optional<Orders> findByUserInfo(Users userInfoDto);
}
