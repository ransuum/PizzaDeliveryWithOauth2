package com.pizza.PizzaDelivery.entity.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UpdatePaymentRequest {
    @Valid
    @NotBlank(message = "Pay status blank")
    private String payStatus;
}
