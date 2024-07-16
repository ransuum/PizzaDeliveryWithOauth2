package com.pizza.PizzaDelivery.repo;

import com.pizza.PizzaDelivery.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepo extends MongoRepository<Users, UUID> {
    UserDetails findByEmail(String email);
    Optional<Users> findUserByEmail(String email);
}
