package com.pizza.PizzaDelivery.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizza.PizzaDelivery.entity.dto.UserInfoDto;
import com.pizza.PizzaDelivery.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {

    @Valid @NotBlank(message = "comments is blank")
    private String comments;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PaymentMethod paymentMethod;
}
