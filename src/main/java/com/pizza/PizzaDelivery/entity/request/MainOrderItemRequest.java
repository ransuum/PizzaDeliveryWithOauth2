package com.pizza.PizzaDelivery.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
@AllArgsConstructor
public class MainOrderItemRequest {

    @Valid @NotBlank(message = "description is blank")
    private String descr;

    @Valid @NotBlank(message = "productId is blank")
    private String productId;

    @Valid @NonNull @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer quantity;
}
