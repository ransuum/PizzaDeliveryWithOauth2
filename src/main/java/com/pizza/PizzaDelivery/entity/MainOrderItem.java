package com.pizza.PizzaDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "main_order_item_oauth")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MainOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    @Schema(description = "Unique identifier of the order item", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    @Schema(description = "Price", example = "15.99")
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
    @Schema(description = "Quantity", example = "2")
    private Integer quantity = 0;

    @OneToMany(mappedBy = "mainOrderItem", fetch = FetchType.LAZY)
    private List<AdditionalOrderItem> additionalOrderItem = new ArrayList<>();

    private LocalDateTime createdAt;
}
