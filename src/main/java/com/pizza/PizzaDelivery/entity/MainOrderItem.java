package com.pizza.PizzaDelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Document("main_order_item") for mongo
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "main_order_item")
public class MainOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String descr;

    //    @DBRef for mongo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders orders;

//    @DBRef for mongo
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private Boolean updated;

    @Column(nullable = false)
    private Integer quantity = 0;

    @OneToMany(mappedBy = "mainOrderItem", fetch = FetchType.LAZY)
    private List<AdditionalOrderItem> additionalOrderItem = new ArrayList<>();

    private LocalDateTime createdAt;
}
