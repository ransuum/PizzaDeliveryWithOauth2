package com.pizza.PizzaDelivery.entity.dto;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.enums.PayStatus;
import com.pizza.PizzaDelivery.enums.PaymentMethod;
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
public class OrderDto {
    private String id;
    private UserInfoDto userInfo;
    private String comments;
    private PaymentMethod paymentMethod;
    private List<MainOrderItem> mainOrderItem = new ArrayList<>();
    private PayStatus payStatus;
    private Double totalPrice;
    private Boolean change;
    private LocalDateTime createdAt;
}
