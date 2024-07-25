package com.pizza.PizzaDelivery.service;

import com.pizza.PizzaDelivery.entity.MainOrderItem;
import com.pizza.PizzaDelivery.entity.Orders;
import com.pizza.PizzaDelivery.entity.Product;
import com.pizza.PizzaDelivery.entity.Users;
import com.pizza.PizzaDelivery.entity.dto.UserInfoDto;
import com.pizza.PizzaDelivery.entity.request.MainOrderItemRequest;
import com.pizza.PizzaDelivery.entity.response.MessageResponse;
import com.pizza.PizzaDelivery.exception.NotFoundException;
import com.pizza.PizzaDelivery.repo.MainOrderItemRepo;
import com.pizza.PizzaDelivery.repo.OrderRepo;
import com.pizza.PizzaDelivery.repo.ProductRepo;
import com.pizza.PizzaDelivery.util.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.pizza.PizzaDelivery.util.LocalDateTimeParser.parse;

@Service
public class MainOrderItemService {

    private final MainOrderItemRepo mainOrderItemRepo;
    private final OrderRepo orderRepo;
    private final Price pizza;
    private final ProductRepo productRepo;

    @Autowired
    public MainOrderItemService(MainOrderItemRepo mainOrderItemRepo, OrderRepo orderRepo, ProductRepo productRepo, Price pizza) {
        this.mainOrderItemRepo = mainOrderItemRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.pizza = pizza;
    }

    public MainOrderItem createMainOrderItem(MainOrderItemRequest mainOrderItemRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = (Users) authentication.getPrincipal();

        Product product = productRepo.findById(mainOrderItemRequest.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));

        Orders orders = orderRepo.findByUserInfo(users)
                .orElseThrow(() -> new NotFoundException("Cannot find orders item by this email:" + users.getEmail()));

        MainOrderItem mainOrderItem = MainOrderItem.builder()
                .orders(orders)
                .quantity(mainOrderItemRequest.getQuantity())
                .product(product)
                .price(pizza.priceOnPizza(product.getCategoryForPizza()) * mainOrderItemRequest.getQuantity())
                .image(product.getImage())
                .updated(Boolean.FALSE)
                .descr(mainOrderItemRequest.getDescr())
                .createdAt(LocalDateTime.now())
                .build();

        orders.getMainOrder().add(mainOrderItem);
        orderRepo.save(orders);

        return mainOrderItemRepo.save(mainOrderItem);
    }

    public MessageResponse deleteMainOrderItemById(String id) {
        MainOrderItem mainOrderItem = mainOrderItemRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Cannot find orders item by this id:" + id));

        mainOrderItemRepo.deleteById(id);

        orderRepo.findById(mainOrderItem.getOrders().getId()).ifPresent(order -> {
            order.getMainOrder().remove(mainOrderItem);
            orderRepo.save(order);
        });

        return new MessageResponse("Successfully deleted main orders item with id " + id);
    }

    public MainOrderItem updateMainOrderItem(String id, String descr, String productId, Integer quantity) {
        MainOrderItem mainOrderItem = mainOrderItemRepo.findById(id).orElseThrow(()
                -> new NotFoundException("Cannot find orders item by this id:" + id));

        if (descr != null && !descr.trim().isEmpty() && !descr.trim().isBlank()) {
            mainOrderItem.setDescr(descr);
            mainOrderItem.setUpdated(Boolean.TRUE);
        }
        if (productId != null && !productId.trim().isEmpty() && !productId.trim().isBlank()) {
            mainOrderItem.setProduct(productRepo.findById(productId).orElseThrow(()-> new NotFoundException("Cannot find product by this id:" + productId)));
            mainOrderItem.setUpdated(Boolean.TRUE);
        }
        if (quantity != null && quantity > 0 && !quantity.equals(mainOrderItem.getQuantity())) {
            mainOrderItem.setQuantity(quantity);
            mainOrderItem.setPrice(quantity * pizza.priceOnPizza(mainOrderItem.getProduct().getCategoryForPizza()));
            mainOrderItem.setUpdated(Boolean.TRUE);
        }
        return mainOrderItemRepo.save(mainOrderItem);
    }

    public List<MainOrderItem> findAllMainOrderByUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = (Users) authentication.getPrincipal();

        Orders orders = orderRepo.findByUserInfo(users)
                .orElseThrow(() -> new NotFoundException("Cannot find orders item by this email:" + users.getEmail()));

        return orders.getMainOrder();
    }

    public List<MainOrderItem> findAllMainOrderItemsByUserAndFilter(Double price, String before, String after,
                                                                    String descr, String id, String productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = (Users) authentication.getPrincipal();

        Orders orders = orderRepo.findByUserInfo(users)
                .orElseThrow(() -> new NotFoundException("Cannot find orders item by this email:" + users.getEmail()));

        List<MainOrderItem> mainOrderItems = orders.getMainOrder();

        if (price != null && price > 0) {
            mainOrderItems = mainOrderItems.stream().filter(mainOrderItem -> mainOrderItem.getPrice().equals(price)).toList();
        }

        if (before != null && !before.trim().isEmpty() && !before.trim().isBlank()) {
            mainOrderItems = mainOrderItems.stream().filter(mainOrderItem -> mainOrderItem.getCreatedAt().isBefore(parse(before))).toList();
        }

        if (after != null && !after.trim().isEmpty() && !after.trim().isBlank()) {
            mainOrderItems = mainOrderItems.stream().filter(mainOrderItem -> mainOrderItem.getCreatedAt().isAfter(parse(after))).toList();
        }

        if (descr != null && !descr.trim().isEmpty() && !descr.trim().isBlank()) {
            mainOrderItems = mainOrderItems.stream().filter(mainOrderItem -> mainOrderItem.getDescr().equals(descr)).toList();
        }

        if (id != null && !id.trim().isEmpty() && !id.trim().isBlank()) {
            mainOrderItems = mainOrderItems.stream().filter(mainOrderItem -> mainOrderItem.getId().equals(id)).toList();
        }

        if (productId != null && !productId.trim().isEmpty() && !productId.trim().isBlank()) {
            mainOrderItems = mainOrderItems.stream().filter(mainOrderItem -> mainOrderItem.getProduct() == productRepo.findById(productId).orElseThrow(()
                    -> new NotFoundException("Product with this id: " + productId + " not found"))).toList();
        }

        return mainOrderItems;
    }
}
