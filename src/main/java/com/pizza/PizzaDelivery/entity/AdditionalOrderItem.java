package com.pizza.PizzaDelivery.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

//@Document("additional_item_order")  MONGO
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@org.springframework.data.relational.core.mapping.Table(name = "additional_item_order")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AdditionalOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Double price;

//    @JsonIgnore
//    @DBRef
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_order_item_id", referencedColumnName = "id")
    private MainOrderItem mainOrderItem;
    //    @JsonIgnore
    //    @DBRef                    MONGO
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @CreatedDate
    private LocalDateTime createdAt;
}
