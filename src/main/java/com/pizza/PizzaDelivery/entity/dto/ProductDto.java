package com.pizza.PizzaDelivery.entity.dto;

import com.pizza.PizzaDelivery.enums.AdditionalItem;
import com.pizza.PizzaDelivery.enums.CategoryForPizza;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
    private String id;
    private CategoryForPizza categoryForPizza;
    private AdditionalItem additionalItem;

    private String description;
    private Double price;
    private String info;
    private String image;

    private Integer discount;
    private Instant createdAt;
    private Instant updatedAt;
}
