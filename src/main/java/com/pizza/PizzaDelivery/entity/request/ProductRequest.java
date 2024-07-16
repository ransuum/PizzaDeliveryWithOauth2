package com.pizza.PizzaDelivery.entity.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductRequest {
    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "category is blank")
    private String category;

    @NotBlank(message = "description is blank")
    private String description;

    @NotBlank(message = "price is blank")
    private Double price;

    @NotBlank(message = "info is blank")
    private String info;

    @NotBlank(message = "image is blank")
    private String image;

    @NotBlank(message = "discount is blank")
    private Integer discount;
}
