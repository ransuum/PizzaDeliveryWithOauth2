package com.pizza.PizzaDelivery.entity.request;

import com.pizza.PizzaDelivery.enums.AdditionalItem;
import com.pizza.PizzaDelivery.enums.CategoryForPizza;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class ProductRequest {

    private AdditionalItem additionalItem;

    private CategoryForPizza categoryForPizza;

    @Valid @NotBlank(message = "description is blank")
    private String description;

    @Valid @NotBlank(message = "info is blank")
    private String info;

    @Valid @NotBlank(message = "image is blank")
    private String image;

    @Valid @NotBlank(message = "discount is blank")
    private Integer discount;
}
