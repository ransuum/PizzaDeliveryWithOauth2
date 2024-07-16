package com.pizza.PizzaDelivery.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersDto {
    private String id;
    private String name;
    private String email;
    private Boolean isAdmin;
    private String password;
}
