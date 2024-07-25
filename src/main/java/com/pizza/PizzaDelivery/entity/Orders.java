package com.pizza.PizzaDelivery.entity;

import com.pizza.PizzaDelivery.entity.dto.UserInfoDto;
import com.pizza.PizzaDelivery.enums.PayStatus;
import com.pizza.PizzaDelivery.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

//@Document("orders") MONGO
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@org.springframework.data.relational.core.mapping.Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable=false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users userInfo;

    private String comments;

    @OneToMany(mappedBy = "orders")
    private List<MainOrderItem> mainOrder = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    private Double totalPrice;
    private Boolean change;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}
