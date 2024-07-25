package com.pizza.PizzaDelivery.entity.request;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.entity.Product;
import com.pizza.PizzaDelivery.enums.AdditionalItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AdditionalOrderItemRequest {
    @Valid @NotBlank(message = "mainOrderItemId is blank")
    @Size(min = 24, message = "too big id")
    private String mainOrderItemId;

    @Valid @NotBlank(message = "productId is blank")
    @Size(min = 24, message = "too big id")
    private String productId;

    @Valid @NonNull
    private Integer quantity;
}
