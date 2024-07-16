package com.pizza.PizzaDelivery.entity.dto;

import com.pizza.PizzaDelivery.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    private String id;

    private Order.UserInfo userInfo;
    private String userId;
    private String comments;
    private List<Order.AdditionalOrderItem> additionalOrder = new ArrayList<>();
    private List<Order.MainOrderItem> mainOrder = new ArrayList<>();
    private String paymentMethod;
    private String payStatus;
    private Double totalPrice;
    private String userAddress;
    private String change;
    private String status;
    private LocalDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String name;
        private String phone;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdditionalOrderItem {
        private String id;
        private String image;
        private Double price;
        private String name;
        private String category;
        private Integer quantity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainOrderItem {
        private String id;
        private String image;
        private Double price;
        private String name;
        private String descr;
        private String category;
        private Integer quantity;
        private String info;
        private List<Order.AdditionalOrderItem> additiveItems = new ArrayList<>();
        private String additiveItemsInfo;
    }
}
