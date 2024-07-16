package com.pizza.PizzaDelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("order")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    private UserInfo userInfo;
    private String userId;
    private String comments;
    private List<AdditionalOrderItem> additionalOrder = new ArrayList<>();
    private List<MainOrderItem> mainOrder = new ArrayList<>();
    private String paymentMethod;
    private String payStatus;
    private Double totalPrice;
    private String userAddress;
    private String change;
    private String status;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private String name;
        private String phone;
        private String email;
    }

    @Data
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
        private List<AdditionalOrderItem> additiveItems = new ArrayList<>();
        private String additiveItemsInfo;
    }
}
