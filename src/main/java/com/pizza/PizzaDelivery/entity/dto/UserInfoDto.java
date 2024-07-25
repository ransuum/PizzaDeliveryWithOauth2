package com.pizza.PizzaDelivery.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfoDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
