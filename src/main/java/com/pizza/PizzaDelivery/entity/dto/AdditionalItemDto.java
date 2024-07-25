package com.pizza.PizzaDelivery.entity.dto;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdditionalItemDto {
    private String id;
    private String image;
    private Double price;
    private MainOrderItem mainOrderItem;
    private Product product;
    private Integer quantity;
    private LocalDateTime createdAt;
}
