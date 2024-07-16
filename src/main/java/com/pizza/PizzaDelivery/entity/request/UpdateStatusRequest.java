package com.pizza.PizzaDelivery.entity.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateStatusRequest {
    @Valid
    @NotBlank(message = "Pay status blank")
    private String status;
}
