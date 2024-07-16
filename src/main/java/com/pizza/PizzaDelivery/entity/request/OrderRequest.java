package com.pizza.PizzaDelivery.entity.request;

import com.pizza.PizzaDelivery.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    private UserInfo userInfo;
    private String userId;
    private String comments;
    private List<Order.AdditionalOrderItem> additionalOrder;
    private List<Order.MainOrderItem> mainOrder;
    private String paymentMethod;
    private Double totalPrice;
    private String userAddress;
    private String payStatus;
    private String change;
    private String status;

    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private String name;
        private String phone;
        private String email;
    }
}
