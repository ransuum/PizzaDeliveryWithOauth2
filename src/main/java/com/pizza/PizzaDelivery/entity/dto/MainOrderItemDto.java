package com.pizza.PizzaDelivery.entity.dto;

import com.pizza.PizzaDelivery.entity.AdditionalOrderItem;
import com.pizza.PizzaDelivery.entity.Orders;
import com.pizza.PizzaDelivery.enums.CategoryForPizza;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MainOrderItemDto {
    private String id;
    private String image;
    private Double price;
    private String descr;
    private Orders orders;
    private CategoryForPizza category;
    private Boolean updated;
    private Integer quantity;
    private List<AdditionalOrderItem> additiveItems = new ArrayList<>();
    private LocalDateTime createdAt;
}
