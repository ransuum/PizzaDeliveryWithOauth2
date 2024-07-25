package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.AdditionalOrderItem;
import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.entity.Product;
import com.pizza.PizzaDelivery.entity.request.AdditionalOrderItemRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.repo.AdditionalOrderRepo;
import com.pizza.PizzaDelivery.repo.MainOrderItemRepo;
import com.pizza.PizzaDelivery.repo.ProductRepo;
import com.pizza.PizzaDelivery.util.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AdditionalOrderItemService {

    private final AdditionalOrderRepo additionalOrderRepo;
    private final MainOrderItemRepo mainOrderItemRepo;
    private final Price price;
    private final ProductRepo productRepo;

    @Autowired
    public AdditionalOrderItemService(AdditionalOrderRepo additionalOrderRepo, MainOrderItemRepo mainOrderItemRepo, ProductRepo productRepo, Price price) {
        this.additionalOrderRepo = additionalOrderRepo;
        this.mainOrderItemRepo = mainOrderItemRepo;
        this.productRepo = productRepo;
        this.price = price;
    }

    public AdditionalOrderItem createAdditionalOrderItem(AdditionalOrderItemRequest additionalOrderItemRequest) {

        MainOrderItem mainOrderItem = mainOrderItemRepo.findById(additionalOrderItemRequest.getMainOrderItemId()).orElseThrow(()
                -> new NotFoundException("Cannot find main orders item by id: " + additionalOrderItemRequest.getMainOrderItemId()));

        Product product = productRepo.findById(additionalOrderItemRequest.getProductId()).orElseThrow(()
                -> new NotFoundException("Cannot find product by id: " + additionalOrderItemRequest.getProductId()));

        AdditionalOrderItem additionalOrderItem = AdditionalOrderItem.builder()
                .quantity(additionalOrderItemRequest.getQuantity())
                .product(product)
                .createdAt(LocalDateTime.now())
                .mainOrderItem(mainOrderItem)
                .image(product.getImage())
                .price(price.priceOnAdditional(product.getAdditionalItem()) * additionalOrderItemRequest.getQuantity())
                .build();

        mainOrderItem.getAdditionalOrderItem().add(additionalOrderItem);
        mainOrderItemRepo.save(mainOrderItem);

        return additionalOrderRepo.save(additionalOrderItem);
    }

    public AdditionalOrderItem updateAdditionalOrderItem(String id, String productId, Integer quantity) {
        AdditionalOrderItem additionalOrderItem1 = additionalOrderRepo.findById(id).orElseThrow(() -> new NotFoundException("Cannot find main orders item by id: " + id));
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException("Cannot find product by id: " + productId));
        additionalOrderItem1.setProduct(product);
        if (quantity != null && quantity > 0) {
            Objects.requireNonNull(additionalOrderItem1).setQuantity(quantity);
            additionalOrderItem1.setPrice(quantity * price.priceOnAdditional(product.getAdditionalItem()));
        }

        return additionalOrderRepo.save(Objects.requireNonNull(additionalOrderItem1));
    }

    public MessageResponse deleteAdditionalOrderItem(String id) {
        AdditionalOrderItem additionalOrderItem1 = additionalOrderRepo.findById(id).orElseThrow(() -> new NotFoundException("Cannot find main orders item by id: " + id));
        additionalOrderRepo.deleteById(id);

        additionalOrderItem1.getMainOrderItem().getAdditionalOrderItem().remove(additionalOrderItem1);
        mainOrderItemRepo.save(additionalOrderItem1.getMainOrderItem());
        return new MessageResponse("Deleted additional orders item for: \n" + additionalOrderItem1.getMainOrderItem());
    }
}
